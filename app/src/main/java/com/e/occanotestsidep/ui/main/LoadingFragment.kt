package com.e.occanotestsidep.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.e.occanotestsidep.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class LoadingFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var dataStateListener: DataStateListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid activity")
        CoroutineScope(IO).launch {
//            delay(100L)
            subscribeObservers()
            trigger()
        }

    }

    private fun trigger() {
        viewModel.setStateEvent(DashboardStateEvent.GetReport())
    }

    private fun subscribeObservers() {
            viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

                println("Debug: Loading DataState: ${dataState} ")

                //handle loading and message
                dataStateListener.onDataStateChange(dataState)


            viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

                viewState.let {
                    println("DEBUG: Loading for ui: ${it}")

                }
            })
        })
    }
}
