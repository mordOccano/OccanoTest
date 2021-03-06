package com.e.occanotestsidep.ui.main.dashboard


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.occanosidetest.utils.TopSpacingItemDecoration

import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.main.DataStateListener
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.ui.main.DashboardStateEvent
import com.e.occanotestsidep.ui.models.*
import kotlinx.android.synthetic.main.fragment_dashboard_main.*
import java.lang.ClassCastException

/**
 * A simple [Fragment] subclass.
 */
class DashboardMainFragment : Fragment(),DasboardMainRvAdapter.Interaction {

    lateinit var viewModel: MainViewModel

    lateinit var dataStateListener: DataStateListener

    lateinit var dashboardMainRvAdapter: DasboardMainRvAdapter

    private val listToComparison = CylindersForComparison()

    var listForMainCalc : ArrayList<MainDashboard> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid activity")

        triggerGetCylindersEvent()
        subscribeObservers()
        initRv()
    }

    private fun initRv(){
        rv_main_dashboard.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(0)
            addItemDecoration(topSpacingItemDecoration)
            dashboardMainRvAdapter = DasboardMainRvAdapter(this@DashboardMainFragment)
            adapter = dashboardMainRvAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            println("Debug: DataState: ${dataState} ")

            //handle loading and message
            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let {
                it.getContentIfNotHandled()?.cylinders?.let {
                    viewModel.setCylinderData(it)
                }
                it.getContentIfNotHandled()?.metadata?.let {
                    viewModel.setMetHadata(it)
                }
            }

        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState.cylinders.let {
                println("DEBUG: Setting cyls for rv: ${it}")

                dashboardMainRvAdapter.submitList(prepareListToCylindersComparison(it))
            }
            viewState.metadata.let {
                println("DEBUG: Setting meta data to somethig good  ")
            }

        })
    }

    fun prepareListBasically(){
        listForMainCalc.let {
            it.add(0, MainDashboard("rpm"))
            it.add(1, MainDashboard("exhaust_temp"))
            it.add(2, MainDashboard("load"))
            it.add(3, MainDashboard("firing pres"))
            it.add(4, MainDashboard("scavenging pres"))
            it.add(5, MainDashboard("compression_pres"))
            it.add(6, MainDashboard("break_power"))
            it.add(7, MainDashboard("imep"))
            it.add(8, MainDashboard("Torque_engine"))
            it.add(9, MainDashboard("bmep"))
            it.add(10, MainDashboard("injection_timing"))
            it.add(11, MainDashboard("fuel_flow"))
        }
    }

    private fun triggerGetCylindersEvent() {
        viewModel.setStateEvent(DashboardStateEvent.GetMainDashboard())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        }catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener ")
        }
    }

    private fun prepareListToCylindersComparison(it: List<Cylinder>?) : List<MainDashboard> {

        prepareListBasically()
        if (it != null) {

            //maybe i will store the value from cylinders. if that calculation was before. its multiply
            for (i in it) {
                listForMainCalc[0].let {gauge ->
                    gauge.maxSpeed = i.rpm.maxValue
                    gauge.value += i.rpm.value/it.size
                    gauge.unit = i.rpm.unit
                    if(i.rpm.healthy != 0){
                        gauge.gaugeHealth = i.rpm.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))
                    }
                }
                listForMainCalc[1].let {gauge ->
                    gauge.unit = i.exhaust_temperature.unit
                    gauge.maxSpeed = i.exhaust_temperature.maxValue
                    gauge.value += i.exhaust_temperature.value/it.size
                    if (i.exhaust_temperature.healthy != 0){
                    gauge.gaugeHealth = i.exhaust_temperature.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))
                    }
                }
                listForMainCalc[2].let {gauge ->
                    gauge.maxSpeed = i.load.maxValue
                    gauge.value+=i.load.value/it.size
                    gauge.unit = i.load.unit
                    if (i.load.healthy != 0){
                        gauge.gaugeHealth = i.load.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))

                    }
                }
                listForMainCalc[3].let {gauge ->
                    gauge.unit = i.firing_pressure.unit
                    gauge.maxSpeed = i.firing_pressure.maxValue
                    gauge.value += i.firing_pressure.value/it.size
                    if(i.firing_pressure.healthy != 0){
                        gauge.gaugeHealth = i.firing_pressure.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))

                    }
                }
                listForMainCalc[4].let {gauge ->
                    gauge.unit = i.scavenging_pressure.unit
                    gauge.maxSpeed = i.scavenging_pressure.maxValue
                    gauge.value += i.scavenging_pressure.value/it.size
                    if (i.scavenging_pressure.healthy != 0) {
                        gauge.gaugeHealth =
                            i.scavenging_pressure.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))

                    }
                }
                listForMainCalc[5].let {gauge ->
                    gauge.unit = i.compression_pressure.unit
                    gauge.maxSpeed = i.compression_pressure.maxValue
                    gauge.value +=i.compression_pressure.value/it.size
                    gauge.gaugeHealth = i.compression_pressure.healthy
                    if (i.compression_pressure.healthy != 0) {
                        gauge.gaugeHealth =
                            i.compression_pressure.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))

                    }
                }
                listForMainCalc[6].let {gauge ->
                    gauge.unit = i.break_power.unit
                    gauge.maxSpeed = i.break_power.maxValue
                    gauge.value += i.break_power.value/it.size
                    gauge.gaugeHealth = i.break_power.healthy
                    if (i.break_power.healthy != 0) {
                        gauge.gaugeHealth =
                            i.break_power.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))

                    }
                }
                listForMainCalc[7].let {gauge ->
                    gauge.unit = i.imep.unit
                    gauge.maxSpeed = i.imep.maxValue
                    gauge.value += i.imep.value/it.size
                    gauge.gaugeHealth = i.imep.healthy
                    if (i.imep.healthy != 0) {
                        gauge.gaugeHealth =
                            i.imep.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))

                    }
                }

                listForMainCalc[8].let {gauge ->
                    gauge.unit = i.torque_engine.unit
                    gauge.maxSpeed = i.torque_engine.maxValue
                    gauge.value +=i.torque_engine.value/it.size
                    gauge.gaugeHealth = i.torque_engine.healthy
                    if (i.torque_engine.healthy != 0) {
                        gauge.gaugeHealth =
                            i.torque_engine.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))

                    }
                }
                listForMainCalc[9].let {gauge ->
                    gauge.unit = i.bmep.unit
                    gauge.maxSpeed = i.bmep.maxValue
                    gauge.value += i.bmep.value/it.size
                    gauge.gaugeHealth = i.bmep.healthy
                    if (i.bmep.healthy != 0) {
                        gauge.gaugeHealth =
                            i.bmep.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))

                    }
                }
                listForMainCalc[10].let {gauge ->
                    gauge.unit = i.injection_timing.unit
                    gauge.maxSpeed = i.injection_timing.maxValue
                    gauge.value +=i.injection_timing.value/it.size
                    gauge.gaugeHealth = i.injection_timing.healthy
                    if (i.injection_timing.healthy != 0) {
                        gauge.gaugeHealth =
                            i.injection_timing.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))

                    }
                }
                listForMainCalc[11].let {gauge ->
                    gauge.unit = i.fuel_flow_rate.unit
                    gauge.maxSpeed = i.fuel_flow_rate.maxValue
                    gauge.value += i.fuel_flow_rate.value/it.size
                    gauge.gaugeHealth = i.fuel_flow_rate.healthy
                    if (i.fuel_flow_rate.healthy != 0) {
                        gauge.gaugeHealth =
                            i.fuel_flow_rate.healthy
                        gauge.status = listOf(Status(statusMainTitle =  "cylinder ${i.numOfCylInEngine} is not normal"))

                    }
                }
            }
            //TODO("define each gauge as well like nir describe in the mail about main dash and then, order the layouts by on interaction to cylinders and from cyls to dashboard and not change the order of sba dush")

        }

        Log.e(
            "prepareListToCylindersComparison",
            "$listForMainCalc"
        )

        return listForMainCalc.toList()
    }

    override fun onItemSelected(position: Int, item: MainDashboard) {
        println("DEBUG: click $position")
        println("DEBUG: click $item")
//        findNavController().navigate(R.id.action_dashboardMainFragment_to_subDadhboardContainer)
    }

}
