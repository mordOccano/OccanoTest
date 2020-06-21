package com.e.occanotestsidep.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.e.occanotestsidep.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ClassCastException
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
        trigger()
        subscribeObservers()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener

        }catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener ")
        }

    }

    override fun onResume() {
        super.onResume()

//        CoroutineScope(Main).launch {
//            delay(100000L)
//        }
        navigateToMain()
    }

    private fun navigateToMain() {
        if (findNavController().currentDestination?.id == R.id.loadingFragment) {
            findNavController().navigate(R.id.action_loadingFragment_to_subDadhboardContainer)}
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
