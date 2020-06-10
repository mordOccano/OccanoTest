package com.e.occanotestsidep.ui.main.alert

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.e.occanosidetest.utils.TopSpacingItemDecoration
import com.e.occanotestsidep.MainActivity

import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.main.DashboardStateEvent
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.ui.models.Alert
import kotlinx.android.synthetic.main.fragment_alert.*
import kotlinx.android.synthetic.main.fragment_status.*
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AlertFragment : Fragment(), AlertAdapter.Interaction {

    private var alertsListForRv:ArrayList<Alert> = ArrayList()
    private var requestQueue: RequestQueue? = null

    private var param1: String? = null
    private var param2: String? = null

    private val TAG: String = "AppDebug"

    lateinit var viewModel: MainViewModel

//    lateinit var dataStateListener: DataStateListener
    lateinit var alertAdapter : AlertAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = activity?.run{
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid Activity")
        triggerData()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert, container, false)
        setUI()
    }

    override fun onResume() {
        super.onResume()
        setUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run{
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid Activity")
       setUI()
    }

    fun setUI(){
        triggerData()
        initRv()
        subscribeObservers()
    }

    private fun triggerData() {
        viewModel.setStateEvent(DashboardStateEvent.GetStatuses())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            setUI()
//            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            println("$context must implement DataStateListener")
        }
    }

    private fun initRv() {
        rv_alert.apply {
            layoutManager = LinearLayoutManager(this@AlertFragment.requireContext())
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(status_rv_new_notification)
            alertAdapter =
                AlertAdapter(this@AlertFragment)
            adapter = alertAdapter
        }
    }

    private fun subscribeObservers() {
        triggerData()
//        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
//            dataStateHandler.onDataStateChange(dataState)
//
//            dataState.data?.let {event ->
//                event.getContentIfNotHandled()?.let {viewState ->
//                    Log.e("dataState","${viewState.statuses}")
//                    viewState.statuses?.let {
//                        viewModel.setStatusesData(it)
//                    }
//                }
//            }
//        })
//
//        viewModel.viewState.observe(viewLifecycleOwner, Observer {
//            Log.e("viewState","${it.statuses}")
//            it.statuses?.let {
//                alertAdapter.submitList(it)
//            }
//        })

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: AlertFragment DataState: ${dataState}")

            // Handle Loading and Message
//            dataStateListener.onDataStateChange(dataState)

            // handle Data<T>
            dataState.data?.let{ event ->
                event.getContentIfNotHandled()?.let{ mainViewState ->
                    println("DEBUG: DataState: ${mainViewState}")
                    mainViewState.statuses?.let{
                       viewModel.setStatusesData(it)

                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState ->
            println("DEBUG: Setting statuses to RecyclerView: ${viewState}")

            viewState.statuses?.let {statuses ->
                // set BlogPosts to RecyclerView
                println("DEBUG: Setting statuses to RecyclerView: ${statuses}")
                prepareAlertList(statuses)
                alertAdapter.submitList(statuses)
            }
        })
    }

    private fun prepareAlertList(statuses: List<Alert>) {
        alertsListForRv.addAll(statuses)

    }

    override fun onItemSelected(position: Int, item: Alert) {
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): AlertFragment {
            return AlertFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }
    }

    private val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteStatus(alertsListForRv[viewHolder.adapterPosition])
            }
        }

    private fun deleteStatus(alert: Alert) {
        alertsListForRv.remove(alert)
        val data = alert.alert_id
        submit(data)
//        viewModel.setStateEvent(DashboardStateEvent.GetStatuses())
        //update the api that status acknowlkedged, in the future
//            GlobalScope.launch {
//                dbk.getStatusDao()?.updateStatusAcknowledge(true,status.statusId)
//            }
        alertAdapter.notifyDataSetChanged()

    }


    override fun onStop() {
        super.onStop()
        requestQueue?.cancelAll(TAG)
    }

    private fun submit(data: String) {
        val URL = "http://${ MainActivity().ip}:4000/archive${data}"
        requestQueue = Volley.newRequestQueue(
            Objects.requireNonNull(requireContext())
        )
        val stringRequest: StringRequest = object : StringRequest(
            Method.PUT,
            URL,
            Response.Listener { response ->
                try {
                    val objres = JSONObject(response)
                } catch (e: JSONException) {
                }
            },
            Response.ErrorListener { error ->
                if (error != null && error.message != null) {
                } else if (error is TimeoutError || error is NoConnectionError) {
                }
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getParamsEncoding(): String {
                return super.getParamsEncoding()
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return try {
                    data.toByteArray(charset("utf-8"))
                } catch (uee: UnsupportedEncodingException) { //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    uee.message!!.toByteArray()
                }
            }
        }
        requestQueue!!.add(stringRequest)
    }

}
