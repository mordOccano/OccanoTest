package com.e.occanotestsidep.ui.main.dashboard


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

import com.e.occanotestsidep.R
//import com.e.occanotestsidep.persistence.Graph.repoGraph.CombPresRepository
//import com.e.occanotestsidep.persistence.Graph.repoGraph.FuelRepository
import com.e.occanotestsidep.ui.models.Cylinder
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

/**
 * A simple [Fragment] subclass.
 */
class GraphsFragment : Fragment() ,View.OnClickListener{

    //TODO(update the fragment for the last update from api - dots[][] for every cylinder)
    var ip: String? = null

    var btnGraphToDash :ImageButton? = null

    var cylsList = ArrayList<Cylinder>()

    var currentCyl:Cylinder? =null

    private var rpm: Float?=0.0f
    private var exhaust_temperature: Float?=0.0f
    private var load: Float?=0.0f
    private var firing_pressure: Float?=0.0f
    private var scavenging_pressure: Float?=0.0f
    private var compression_pressure: Float?=0.0f
    private var break_power: Float?=0.0f
    private var imep: Float?=0.0f
    private var torque_engine: Float?=0.0f
    private var bmep: Float?=0.0f
    private var injection_timing: Float?=0.0f
    private var fuel_flow_rate: Float?=0.0f

    var torqueGraph: GraphView? = null
    var bmepGraph: GraphView? = null
    var scaveGraph: GraphView? = null
    var imepGraph: GraphView? = null
    var breakGraph: GraphView? = null
    var injecGraph: GraphView? = null
    var rpmGraph: GraphView? = null
    var compGraph: GraphView? = null
    var exsahustGraph: GraphView? = null
    var firingGraph: GraphView? = null
    var loadGraph: GraphView? = null
    var fuelGraph: GraphView? = null

    var torqueSeries: LineGraphSeries<DataPoint>? = null
    var bmepSeries: LineGraphSeries<DataPoint>? = null
    var scaveSeries: LineGraphSeries<DataPoint>? = null
    var imepSeries: LineGraphSeries<DataPoint>? = null
    var breakSeries: LineGraphSeries<DataPoint>? = null
    var injectSeries: LineGraphSeries<DataPoint>? = null
//    var rpmSeries: LineGraphSeries<DataPoint>? = null
    var compSeries: LineGraphSeries<DataPoint>? = null
    var exshaustSeries: LineGraphSeries<DataPoint>? = null
    var fuelSeries: LineGraphSeries<DataPoint>? = null
    var loadSeries: LineGraphSeries<DataPoint>? = null
    var firingSeries: LineGraphSeries<DataPoint>? = null

    private var graph2LastXValue: Double = 0.0

//    lateinit var combPresRepository: CombPresRepository
//    lateinit var fuelRepository: FuelRepository
//    lateinit var cylsRepository: CylsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        combPresRepository = CombPresRepository(context)
//        fuelRepository = FuelRepository(context)
//        cylsRepository = CylsRepository(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graphs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPointer(view)
        initGraphs()
        setListeners()
        retriveCyls()

    }

    private fun setListeners() {


        torqueGraph!!.setOnClickListener{}
        bmepGraph!!.setOnClickListener{}
        scaveGraph!!.setOnClickListener{}
        imepGraph!!.setOnClickListener{}
        breakGraph!!.setOnClickListener{}
        injecGraph!!.setOnClickListener{}
        rpmGraph!!.setOnClickListener{}
        compGraph!!.setOnClickListener{}
        exsahustGraph!!.setOnClickListener{}
        firingGraph!!.setOnClickListener{}
        loadGraph!!.setOnClickListener{}
        fuelGraph!!.setOnClickListener{}
        btnGraphToDash!!.setOnClickListener(this)
    }






    private fun retriveCyls() {

//        this.cylsRepository!!.retriveCylTask()
//            .observe(viewLifecycleOwner,
//                Observer<List<Cylinder>> { cylinders ->
//                    if (cylsList.size > 0) {
//                        cylsList.clear()
//                    }
//                    if (cylinders != null) {
//                        cylsList.addAll(cylinders)
////                        logsList.reverse()
//                        currentCyl = cylsList.last()
//                        setGraph(currentCyl!!)
//                    }
//
//
////                    logAdapter.notifyDataSetChanged()
//                })


    }



    private fun initGraphs() {
        with(torqueGraph) {
            this!!.addSeries(torqueSeries)
            viewport.isXAxisBoundsManual = true
            viewport.isScrollable = true
            viewport.setMinX(0.0)
            viewport.setMaxX(40.0)
        }
        with(bmepGraph) {
            this!!.addSeries(bmepSeries)
            viewport.isXAxisBoundsManual = true
            viewport.isScrollable = true
            viewport.setMinX(0.0)
            viewport.setMaxX(40.0)
        }
        with(imepGraph) {
            this!!.addSeries(imepSeries)
            viewport.isXAxisBoundsManual = true
            viewport.isScrollable = true
            viewport.setMinX(0.0)
            viewport.setMaxX(40.0)
        }
        with(breakGraph) {
            this!!.addSeries(breakSeries)
            viewport.isXAxisBoundsManual = true
            viewport.isScrollable = true
            viewport.setMinX(0.0)
            viewport.setMaxX(40.0)
        }
        with(injecGraph) {
            this!!.addSeries(injectSeries)
            viewport.isXAxisBoundsManual = true
            viewport.isScrollable = true
            viewport.setMinX(0.0)
            viewport.setMaxX(40.0)
        }
        with(scaveGraph) {
            this!!.addSeries(scaveSeries)
            viewport.isXAxisBoundsManual = true
            viewport.isScrollable = true
            viewport.setMinX(0.0)
            viewport.setMaxX(40.0)
        }
        with(compGraph) {
            this!!.addSeries(compSeries)
            viewport.isXAxisBoundsManual = true
            viewport.isScrollable = true
            viewport.setMinX(0.0)
            viewport.setMaxX(40.0)
        }
        with(exsahustGraph) {
            this!!.addSeries(exshaustSeries)
            viewport.isXAxisBoundsManual = true
            viewport.isScrollable = true
            viewport.setMinX(0.0)
            viewport.setMaxX(40.0)
        }
        with(firingGraph) {
            this!!.addSeries(firingSeries)
            viewport.isXAxisBoundsManual = true
            viewport.isScrollable = true
            viewport.setMinX(0.0)
            viewport.setMaxX(40.0)
        }
        with(loadGraph) {
            this!!.addSeries(loadSeries)
            viewport.isXAxisBoundsManual = true
            viewport.setMinX(0.0)
            viewport.isScrollable = true
            viewport.setMaxX(40.0)
        }
        with(fuelGraph) {
            this!!.addSeries(fuelSeries)
            viewport.isXAxisBoundsManual = true
            viewport.setMinX(0.0)
            viewport.isScrollable = true
            viewport.setMaxX(40.0)
        }


    }


    private fun setPointer(v: View) {

        //graph
        torqueGraph = v.findViewById(R.id.torque_graph) as GraphView
        bmepGraph = v.findViewById(R.id.bmep_graph) as GraphView
        scaveGraph = v.findViewById(R.id.scave_graph) as GraphView
        imepGraph = v.findViewById(R.id.imep_graph) as GraphView
        breakGraph = v.findViewById(R.id.break_graph) as GraphView
        injecGraph = v.findViewById(R.id.injec_graph) as GraphView
        rpmGraph = v.findViewById(R.id.rpm_graph) as GraphView
        compGraph = v.findViewById(R.id.comp_pres_graph) as GraphView
        exsahustGraph = v.findViewById(R.id.exshaust_graph) as GraphView
        firingGraph = v.findViewById(R.id.firing_pres_graph) as GraphView
        loadGraph = v.findViewById(R.id.load_graph) as GraphView
        fuelGraph = v.findViewById(R.id.fuel_graph) as GraphView

        torqueSeries = LineGraphSeries()
        bmepSeries = LineGraphSeries()
        scaveSeries = LineGraphSeries()
        imepSeries = LineGraphSeries()
        breakSeries = LineGraphSeries()
        injectSeries = LineGraphSeries()
//        rpmSeries = LineGraphSeries()
        compSeries = LineGraphSeries()
        exshaustSeries = LineGraphSeries()
        firingSeries = LineGraphSeries()
        loadSeries = LineGraphSeries()
        fuelSeries = LineGraphSeries()

        btnGraphToDash = v.findViewById(R.id.btn_graph_to_dash)

    }

//    private fun setGraph(cylinder: Cylinder) {
//        graph2LastXValue++
//        torqueSeries!!.appendData(
//            DataPoint(graph2LastXValue, cylinder.Torque_engine_value.toDouble()),
//            true,
//            40
//        )
//        bmepSeries!!.appendData(DataPoint(graph2LastXValue, cylinder.bmep_value.toDouble()), true, 40)
//        scaveSeries!!.appendData(
//            DataPoint(graph2LastXValue, cylinder.scavenging_pressure_value.toDouble()),
//            true,
//            40
//        )
//        imepSeries!!.appendData(DataPoint(graph2LastXValue, cylinder.imep_value.toDouble()), true, 40)
//        breakSeries!!.appendData(
//            DataPoint(graph2LastXValue, cylinder.break_power_value.toDouble()),
//            true,
//            40
//        )
//        injectSeries!!.appendData(
//            DataPoint(graph2LastXValue, cylinder.injection_timing_value.toDouble()),
//            true,
//            40
//        )
////        rpmSeries!!.appendData(DataPoint(graph2LastXValue,cylinder.rpm!!.toDouble()), true, 40)
//        compSeries!!.appendData(
//            DataPoint(graph2LastXValue, cylinder.compression_pressure_value.toDouble()),
//            true,
//            40
//        )
//        exshaustSeries!!.appendData(
//            DataPoint(
//                graph2LastXValue,
//                cylinder.exhaust_temperature_value.toDouble()
//            ), true, 40
//        )
//        loadSeries!!.appendData(DataPoint(graph2LastXValue, cylinder.load_value.toDouble()), true, 40)
//        firingSeries!!.appendData(
//            DataPoint(graph2LastXValue,cylinder.firing_pressure_value.toDouble()),
//            true,
//            40
//        )
//        fuelSeries!!.appendData(
//            DataPoint(graph2LastXValue, cylinder.fuel_flow_rate_value.toDouble()),
//            true,
//            40
//        )

//        Toast.makeText(context,torque_engine.toString() + bmep.toString() + scavenging_pressure.toString(),Toast.LENGTH_SHORT).show()
//    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.btn_graph_to_dash ->{
                if(findNavController().currentDestination?.id == R.id.graphsFragment){
                    findNavController().navigate(R.id.action_graphsFragment_to_dashFragment)
                }
                if (findNavController().currentDestination?.id == R.id.subDadhboardContainer){
                    findNavController().navigate(R.id.action_subDadhboardContainer_to_dashFragment)
                }
            }
      }
    }

}