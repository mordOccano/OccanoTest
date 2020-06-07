package com.e.occanotestsidep.ui.main.dashboard

import android.content.Context
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
import com.e.occanotestsidep.ui.main.DataStateListener
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.ui.models.Alert
import kotlinx.android.synthetic.main.fragment_alert.*

/**
 * A simple [Fragment] subclass.
 */
class AlertFragment : Fragment(), AlertAdapter.Interaction {

    private val TAG: String = "AppDebug"

    lateinit var viewModel: MainViewModel

    lateinit var dataStateHandler: DataStateListener
    lateinit var alertAdapter : AlertAdapter

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            println("$context must implement DataStateListener")
        }
    }

    private fun initRv() {
        rv_alert.apply {
            layoutManager = LinearLayoutManager(this@AlertFragment.requireContext())
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            alertAdapter =
                AlertAdapter(this@AlertFragment)
            adapter = alertAdapter

        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            dataStateHandler.onDataStateChange(dataState)

            dataState.data?.let {event ->
                event.getContentIfNotHandled()?.let {viewState ->
                    println(viewState.toString())

                    viewState.statuses?.let {
                        viewModel.setStatusesData(it)
                    }

                }

            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState->
            viewState.statuses?.let {
                alertAdapter.submitList(it)

                println("________----------${it.toString()} ----------")
            }
        })
    }

    override fun onItemSelected(position: Int, item: Alert) {
    }

}
