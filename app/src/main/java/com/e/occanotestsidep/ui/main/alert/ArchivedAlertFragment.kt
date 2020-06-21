package com.e.occanotestsidep.ui.main.alert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.occanosidetest.utils.TopSpacingItemDecoration

import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.main.DashboardStateEvent
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.ui.models.Alert
import kotlinx.android.synthetic.main.fragment_alert.*

/**
 * A simple [Fragment] subclass.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ArchivedAlertFragment : Fragment(), ArchivedAlertAdapter.Interaction {

    private var param1: String? = null
    private var param2: String? = null

    private val TAG: String = "AppDebug"

    lateinit var viewModel: MainViewModel

//    lateinit var dataStateListener: DataStateListener
    lateinit var alertAdapter : ArchivedAlertAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    override fun onResume() {
        super.onResume()
        triggerData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run{
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid Activity")
        initRv()
        subscribeObservers()
    }

    private fun triggerData() {
        viewModel.setStateEvent(DashboardStateEvent.GetStatuses())
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try {
//            dataStateListener = context as DataStateListener
//        } catch (e: ClassCastException) {
//            println("$context must implement DataStateListener")
//        }
//    }

    private fun initRv() {
        rv_alert.apply {
            layoutManager = LinearLayoutManager(this@ArchivedAlertFragment.requireContext())
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            alertAdapter =
                ArchivedAlertAdapter(this@ArchivedAlertFragment)
            alertAdapter.submitList(viewModel.archiveAlertList.toList())
            adapter = alertAdapter
        }
    }

    private fun subscribeObservers() {
        triggerData()
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: AlertFragment DataState: ${dataState}")

            // Handle Loading and Message
//            dataStateListener.onDataStateChange(dataState)

            // handle Data<T>
            dataState.data?.let{ event ->
                event.getContentIfNotHandled()?.let{ mainViewState ->
                    println("DEBUG: DataState: ${mainViewState}")
                    mainViewState.archiveStatuses?.let{
                       viewModel.setArchivedStatusesData(it)

                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState ->
            println("DEBUG: Setting statuses to RecyclerView: ${viewState}")

            viewState.archiveStatuses?.let {statuses ->
                // set BlogPosts to RecyclerView
                println("DEBUG: Setting statuses to RecyclerView: ${statuses}")
            }
        })
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
        fun newInstance(sectionNumber: Int): ArchivedAlertFragment {
            return ArchivedAlertFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }
    }

}
