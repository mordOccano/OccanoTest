package com.e.occanotestsidep.ui.main.status

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.e.occanosidetest.utils.TopSpacingItemDecoration
import com.e.occanotestsidep.MainActivity
import com.e.occanotestsidep.R
import com.e.occanotestsidep.persistence.AppDBK
import com.e.occanotestsidep.ui.main.DashboardStateEvent
import com.e.occanotestsidep.ui.main.DataStateListener
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.ui.models.Alert
import com.e.occanotestsidep.ui.models.Status
import kotlinx.android.synthetic.main.fragment_calibration_fragment_z.*
import kotlinx.android.synthetic.main.fragment_status.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.*
import kotlin.collections.ArrayList

//private  const val ARG_PARAM1 = "StatusFragment"
//private const val ARG_PARAM2 = "1"

class StatusFragment :Fragment(), NewStatusAdapter.Interaction, View.OnClickListener {

    private var requestQueue: RequestQueue? = null

    private val TAG: String = "StatusFragment"

    lateinit var viewModel: MainViewModel
    lateinit var dataStateListener: DataStateListener
//    var statusesList:ArrayList<Status> = ArrayList()
//    var statusesListFromCache = ArrayList<Status>()

    var statusesListFromApi = ArrayList<Status>()
    private var statusesListForRv:ArrayList<Alert> = ArrayList()
    lateinit var statusNewRVAdapter: NewStatusAdapter
//    private lateinit var dbk: AppDBK
//
//    private var param1: String? = null
//    private var param2: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            StatusFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_status,container,false)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG,"onAttach")
        try {
            dataStateListener = context as DataStateListener
        }catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener ")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG,"on resume")
        setUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mStatusRepository = StatusRepository(context)
        Log.e(TAG,"onViewCreated")
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid activity")
        triggerGetStatusesEvent()
        subscribeObservers()
        setUI()
    }

    private fun setUI() {
//        dbk = AppDBK(requireContext())
//        viewModel = activity?.run {
//            ViewModelProvider(this).get(MainViewModel::class.java)
//        }?:throw Exception("Invalid activity")
        setListeners()
        initNewRv()
//        initFakeRvList()
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            println("Debug: DataState: ${dataState} ")

            //handle loading and message
            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let {
                it.getContentIfNotHandled()?.statuses?.let {
                    viewModel.setStatusesData(it)
                    statusNewRVAdapter.submitList(it)
                }
            }

        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState.statuses.let {
//                Log.e("DEBUG: Setting statuses for rv: ","$it")
                it?.let {
//                    statusesListForRv.addAll(it)
                    statusNewRVAdapter.submitList(it)
//                    prepareStatusList(it)
                }
            }
        })
    }

    fun prepareStatusList(it: List<Status>){

//        statusesListFromApi.addAll(it)
//        GlobalScope.launch {
//
//            val list = (dbk.getStatusDao()?.getAllStatuses()!!.toMutableList())
//            Log.e("from cache ${TAG}",dbk.getStatusDao()?.getAllStatuses()!!.toMutableList().toString())
//
//            withContext(Main){
//                statusesListForRv.addAll(list)
//                prepareSubList()
//                statusNewRVAdapter.submitList(statusesListForRv)
//            }
//
////            for (i in statusesListForRv) {
////                dbk.getStatusDao()?.deleteStatus(i)
////                dbk.getStatusDao()?.insertStatus(i)
////            }
//
//            Log.e("second from cache ${TAG}",dbk.getStatusDao()?.getAllStatuses()!!.toMutableList().toString())
//            Log.e("statusesListForRv ${TAG}",statusesListForRv.toString())

//        }
    }

    private fun prepareSubList(){
//        for (i in statusesListForRv){
//            if (!i.kindOfAcknowledge){
//                for (j in statusesListFromApi){
//                    if (i.statusId == j.statusId){
//                        statusesListFromApi.remove(j)
//                    }
//                }
//            }
//        }
//        statusesListForRv.union(statusesListFromApi)
        Log.e("statusesListForRv ${TAG}",statusesListForRv.toString())
        statusNewRVAdapter.notifyDataSetChanged()
    }

    private fun triggerGetStatusesEvent() {
        viewModel.setStateEvent(DashboardStateEvent.GetStatuses())
    }

    private fun setListeners() {
        view?.findViewById<ImageButton>(R.id.btn_status_to_dashboard)?.setOnClickListener(this)
        view?.findViewById<ImageButton>(R.id.btn_status_to_archive)?.setOnClickListener(this)
        view?.findViewById<ImageButton>(R.id.btn_status_current)?.setOnClickListener(this)
    }

    private fun initFakeRvList() {
//        for (i in 1..20){
//            statusesList.add(Status(i,1,"Main Title", "sub title", "subTite","sub moria contenta sub more content sub moria contenta sub more content",
//                1,true, "(Utility.getCurrentTimeStamp().plus(i)).toString())"))
//        }
//        if (statusesListForRv.isEmpty()) {
//            statusesListForRv.add(Status("1",4,"Efficiency", " Cylinder #4","","Knocking Identified - Ignition Timing (deg): 0.7",0,false,"06:53:12 UTC  01/04/2020"))
//            statusesListForRv.add(Status("3",2,"AlertFragment", "Cylinder #2","Compression pressure fault","* comp. press: -1.35 std   * Possibly BlowBy - Piston Rings",1,true,"01:23:41 UTC  29/03/2020"))
//            statusNewRVAdapter.notifyDataSetChanged()
//        }
    }


    private fun initNewRv() {
        status_rv_new_notification.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(status_rv_new_notification)
            statusNewRVAdapter = NewStatusAdapter(this@StatusFragment)
            adapter = statusNewRVAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Alert) {

////        val status = Status(item.statusId,item.cylinder_num,item.statusMainTitle,item.statusSubTitle,item.statusLessContent,item.statusMoreContent,item.statusKindOfDanger,!item.kindOfAcknowledge,item.timeStampOfstatus)
////        statusesListForRv[position].kindOfAcknowledge = !item.kindOfAcknowledge
//        mStatusRepository.deleteStatus(statusesListForRv[position])
//        //insert for db for archive
//        statusesListForRv.remove(item)
//        statusNewRVAdapter.notifyItemRemoved(position)


//        notifyrv()

//        updateList()
//        initFakeRvList()
//        println(statusesList.get(position).toString())

    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_status_to_archive->{
//                v.findNavController().navigate(R.id.action_statusFragment_to_statusArchiveFragment)
            }

            R.id.btn_status_to_dashboard->{
//                v.findNavController().navigate(R.id.action_statusFragment_to_dashFragment)
            }
            else->{
                Toast.makeText(this.context,"try again", Toast.LENGTH_SHORT).show()
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
                deleteStatus(statusesListForRv[viewHolder.adapterPosition])
            }
        }

    private fun deleteStatus(alert: Alert) {
        statusesListForRv.remove(alert)
        val data = alert.alert_id

        submit(data)

//        viewModel.setStateEvent(DashboardStateEvent.GetStatuses())
        //update the api that status acknowlkedged, in the future
//            GlobalScope.launch {
//                dbk.getStatusDao()?.updateStatusAcknowledge(true,status.statusId)
//            }
        statusNewRVAdapter.notifyDataSetChanged()

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