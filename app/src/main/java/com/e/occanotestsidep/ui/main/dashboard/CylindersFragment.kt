package com.e.occanotestsidep.ui.main.dashboard


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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.occanosidetest.utils.TopSpacingItemDecoration

import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.main.DashboardStateEvent
import com.e.occanotestsidep.ui.main.DataStateListener
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.CylindersForComparison
import com.e.occanotestsidep.ui.models.Gauge
import com.e.occanotestsidep.ui.models.GaugeForCalibration
//import com.e.occanotestsidep.persistence.Graph.repoGraph.CombPresRepository
//import com.e.occanotestsidep.persistence.Graph.repoGraph.FuelRepository
import kotlinx.android.synthetic.main.fragment_cylinders.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ClassCastException

/**
 * A simple [Fragment] subclass.
 */
class CylindersFragment : Fragment() ,View.OnClickListener, CylindersRvAdapter.Interaction{

    lateinit var viewModel: MainViewModel
    lateinit var dataStateListener: DataStateListener
    lateinit var cylinderRvAdapter: CylindersRvAdapter
    var gaugeForCalibration = GaugeForCalibration()

    private val listToComparison = CylindersForComparison()
    val cylsInShortWay = ArrayList<Gauge>()

    var unitName: String = ""
    var maxSpeed: Float = 0f
    var stamAvg = 0.0f


    var btnToDash : ImageButton? = null

    var ip: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cylinders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid activity")

        subscribeObservers()
        setPointers(view)
        initRv()
        setListeners()

        gaugeForCalibration = GaugeForCalibration()
        gaugeForCalibration.value = 1f
        getGaugeToCalib()
        setMaxGauges(maxSpeed)
        setGauges()
        triggerGetCylindersEvent()
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            println("Debug: CylindersFragment DataState: ${dataState} ")

            //handle loading and message
            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let {
                it.getContentIfNotHandled()?.cylinders?.let {
                    viewModel.setCylinderData(it)
                }
            }

        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState.cylinders.let {
                println("DEBUG: CylindersFragment Setting cyls for ui: ${it}")


                it?.let {
                    cylinderRvAdapter.submitList(prepareListToCylindersComparison(it))
                }

            }
        })
    }

    private fun prepareListToCylindersComparison(it: List<Cylinder>?) : List<List<Gauge>> {

        GlobalScope.launch {
            if (it != null) {
            for (i in it) {
                with(listToComparison) {
                    rpm_gaugeList.add(i.numOfCylInEngine - 1, i.rpm)
                    exhaust_temperature_gaugeList.add(i.numOfCylInEngine - 1, i.exhaust_temperature)
                    load_gaugeList.add(i.numOfCylInEngine - 1, i.load)
                    firing_pressure_gaugeList.add(i.numOfCylInEngine - 1, i.firing_pressure)
                    scavenging_pressure_gaugeList.add(i.numOfCylInEngine - 1, i.scavenging_pressure)
                    compression_pressure_gaugeList.add(i.numOfCylInEngine - 1, i.compression_pressure)
                    break_power_gaugeList.add(i.numOfCylInEngine - 1, i.break_power)
                    imep_gaugeList.add(i.numOfCylInEngine - 1, i.imep)
                    Torque_engine_gaugeList.add(i.numOfCylInEngine - 1, i.torque_engine)
                    bmep_gaugeList.add(i.numOfCylInEngine - 1, i.bmep)
                    injection_timing_gaugeList.add(i.numOfCylInEngine - 1, i.injection_timing)
                    fuel_flow_rate_gaugeList.add(i.numOfCylInEngine - 1, i.fuel_flow_rate)
                }

            }
        }

        }
        val list : List<List<Gauge>> = listOf(
               listToComparison.rpm_gaugeList.toList(),
               listToComparison.exhaust_temperature_gaugeList.toList(),
               listToComparison.load_gaugeList.toList(),
               listToComparison.firing_pressure_gaugeList.toList(),
               listToComparison.scavenging_pressure_gaugeList.toList(),
               listToComparison.compression_pressure_gaugeList.toList(),
               listToComparison.break_power_gaugeList.toList(),
               listToComparison.imep_gaugeList.toList(),
               listToComparison.Torque_engine_gaugeList.toList(),
               listToComparison.bmep_gaugeList.toList(),
               listToComparison.injection_timing_gaugeList.toList(),
               listToComparison.fuel_flow_rate_gaugeList.toList()
        )



    Log.e(
        "prepareListToCylindersComparison",
        "${list}"
    )



        return list
    }

    private fun initRv(){
        rv_cylinders.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(0)
            addItemDecoration(topSpacingItemDecoration)
            cylinderRvAdapter = CylindersRvAdapter(this@CylindersFragment)
            adapter = cylinderRvAdapter
        }
    }

    private fun triggerGetCylindersEvent() {
        viewModel.setStateEvent(DashboardStateEvent.GetCylinders())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        }catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener ")
        }
    }


//    private fun setUnitText() {
//        cylinders_unit.text = unitName
//    }
//
//    private fun getData() {
//
//        when (this.gaugeForCalibration.name){
//            "torque_gauge"-> {
//                cylinders_kind.text = "torque"
//                unitName = "KN/m"
//                maxSpeed = (StaticAddress.max_torque_gauge)
////                return
//            }
//            "load_gauge"-> {
//                calib_d_gauge_name.text = "load"
//                unitName ="%"
//                maxSpeed = (StaticAddress.max_load_gauge)
//            }
//            "firing_pres_gauge"-> {
//                cylinders_kind.text = "firing pressure"
//                unitName ="bar"
//                maxSpeed = (StaticAddress.max_firing_pres_gauge)
//            }
//            "bmep_gauge"-> {
//                cylinders_kind.text ="bmep"
//                unitName        = "bar"
//                maxSpeed        = (StaticAddress.max_bmep_gauge)
//            }
//            "break_power_gauge"-> {
//                cylinders_kind.text ="break power"
//                unitName        =("Kw")
//                maxSpeed        =(StaticAddress.max_break_power_gauge)
//            }
//            "comp_pres_gauge"-> {
//                cylinders_kind.text = "comppression presssure"
//                unitName        = ("bar")
//                maxSpeed        = (StaticAddress.max_comp_pres_gauge)
//            }
//            "engine_speed_gauge"-> {
//                cylinders_kind.text ="engine speed"
//                unitName        = "Rpm"
//                maxSpeed        = (StaticAddress.max_engine_speed_gauge)
//            }
//            "exhaust_gauge"-> {
//                cylinders_kind.text = "exhaust"
//                unitName        = ("C°")
//                maxSpeed        = (StaticAddress.max_exhaust_gauge)
//            }
//            "fuel_gauge"-> {
//                cylinders_kind.text = "fuel"
//                unitName        = ("Kg/h")
//                maxSpeed        = (StaticAddress.max_fuel_gauge)
//            }
//            "imep_gauge"-> {
//                cylinders_kind.text = "imep"
//                unitName        = ("")
//                maxSpeed        = (StaticAddress.max_imep_gauge)
//                //TODO: update the unit
//            }
//            "injec_gauge"-> {
//                cylinders_kind.text = "injec"
//                unitName        =("bar")
//                maxSpeed        =(StaticAddress.max_injec_gauge)
//
//
//
//            }
//            "scave_gauge"-> {
//                cylinders_kind.text = "scave"
//                unitName        =("bar")
//                maxSpeed        = (StaticAddress.max_scave_gauge)
//            }
//            else -> {
//                cylinders_kind.text = this.gaugeForCalibration.name
//                unitName        = ("")
//                maxSpeed        = (this.gaugeForCalibration.value*2)
//
//            }
//
//        }
//        stamAvg = gaugeForCalibration.value
//
//    }

    override fun onResume() {
        super.onResume()
//        setPointers(requireView())
//        setMaxGauges(maxSpeed)
//        setGauges()
        triggerGetCylindersEvent()
    }


    private fun setPointers(v:View) {
        btnToDash = v.findViewById(R.id.btn_cyl_to_dash)

    }

    private fun setListeners() {
//        btnToDash!!.setOnClickListener(this)
    }

    //setUnit(name:String:

    private fun setMaxGauges(maxValue:Float) {
    }

    private fun setGauges() {
    }


    override fun onClick(v: View?) {
        when(v!!.id){
//            R.id.btn_cyl_to_dash ->{
//                if (findNavController().currentDestination?.id == R.id.cylindersFragment) {
//                    findNavController().navigate(R.id.action_cylindersFragment_to_dashFragment)}
//                if (findNavController().currentDestination?.id == R.id.subDadhboardContainer){
//                    findNavController().navigate(R.id.action_subDadhboardContainer_to_dashFragment)
//                }
//            }
        }
    }

    fun getGaugeToCalib(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        var defaultName = "scave_gauge"
        var defaultValue = 0.0f
        gaugeForCalibration.name = sharedPref.getString("nameOfGaugeFromdash", defaultName).toString()
        gaugeForCalibration.value = sharedPref.getFloat("valueOfGaugeFromdash",defaultValue)
    }

    override fun onItemSelected(position: Int, item: List<Gauge>) {
//        if (findNavController().currentDestination?.id == R.id.cylindersFragment) {
//            findNavController().navigate(R.id.action_cylindersFragment_to_dashFragment)}
//        if (findNavController().currentDestination?.id == R.id.subDadhboardContainer){
//            findNavController().navigate(R.id.action_subDadhboardContainer_to_dashFragment)
//        }
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
        fun newInstance(sectionNumber: Int): CylindersFragment {
            return CylindersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

}
