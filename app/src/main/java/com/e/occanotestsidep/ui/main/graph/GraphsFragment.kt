package com.e.occanotestsidep.ui.main.graph


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.main.DataStateListener
import com.e.occanotestsidep.ui.main.MainViewModel
//import com.e.occanotestsidep.persistence.Graph.repoGraph.CombPresRepository
//import com.e.occanotestsidep.persistence.Graph.repoGraph.FuelRepository
import com.e.occanotestsidep.ui.models.GraphDots
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class GraphsFragment : Fragment() ,View.OnClickListener{

    //mvi example
    /**
    1. properties
    lateinit var viewModel : ViewModel

    --ViewModel

    2.
    lateinit var dataStateListener: DataStateListener

    3.in onViewCreated
    viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
                 }?:throw Exception("Invalid activity")


     4.
    private fun subscribeObservers() {
    viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
    println("Debug: DataState: $dataState")

    dataStateListener.onDataStateChange(dataState)

    dataState.data?.let {
    it.getContentIfNotHandled()?.graphDots?.let {
    viewModel.setGraphData(it)
    }
    }

    viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
    viewState.graphDots.let{
    println( "Debug: setting graph to view")
    it?.let{
    prepareTheDataForGraph(it)
    }
    }
    })
    })
    }

     */

    /**
     * recyclerview example
     *
     * lateinit var RvAdapter: RvAdapter
     */

    private val TAG: String = "GraphFragment"

    lateinit var viewModel : MainViewModel
    lateinit var dataStateListener: DataStateListener



    //TODO(update the fragment for the last update from api - dots[][] for every cylinder)
    var btnGraphToDash :ImageButton? = null

    private var engin: Float?=0.0f

    var engineGraph: GraphView? = null

    var engineSeries: LineGraphSeries<DataPoint>? = null


    private var graph2LastXValue: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graphs, container, false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
                 }?:throw Exception("Invalid activity")

        setPointer(view)
        initGraphs()
        setListeners()
        subscribeObservers()

    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("Debug: DataState: $dataState")

            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let {
                it.getContentIfNotHandled()?.graphDots?.let {
                    viewModel.setGraphData(it)
                }
            }

            viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
                viewState.graphDots.let{
                    println( "Debug: setting graph to view")
                    it?.let{
                        prepareTheDataForGraph(it)
                    }
                }
            })
        })
    }

    private fun prepareTheDataForGraph(it: List<GraphDots>) {

    }

    private fun setListeners() {
        engineGraph!!.setOnClickListener{}
        btnGraphToDash!!.setOnClickListener(this)
    }

    private fun initGraphs() {
        with(engineGraph) {
            this!!.addSeries(engineSeries)
            with(viewport){
                isXAxisBoundsManual = true
                isScrollable = true
                setMinX(0.0)
                setMaxX(40.0)
            }
        }
    }

    //TODO("understand how to present bunch of gauges on one graph yalla bye")


    private fun setPointer(v: View) {

        //graph
        engineGraph = v.findViewById(R.id.torque_graph) as GraphView

        engineSeries = LineGraphSeries()

        btnGraphToDash = v.findViewById(R.id.btn_graph_to_dash)

    }

//    private fun setGraph(cylinder: Cylinder) {
//        graph2LastXValue++
//        torqueSeries!!.appendData(
//            DataPoint(graph2LastXValue, cylinder.Torque_engine_value.toDouble()),
//            true,
//            40
//        )
//
//        Toast.makeText(context,torque_engine.toString() + bmep.toString() + scavenging_pressure.toString(),Toast.LENGTH_SHORT).show()
//    }


    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.btn_graph_to_dash ->{

            }
      }
    }

}