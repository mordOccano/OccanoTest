package com.e.occanotestsidep.ui.main.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_bmep_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_break_power_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_comp_pres_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_engine_speed_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_exhaust_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_firing_pres_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_fuel_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_imep_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_injec_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_load_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_scave_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.calib_torque_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.bmep_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.break_power_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.compression_pressure_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.exshaust_temperature_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.firing_pressure_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.fuel_flow_rate_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.imep_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.injection_timing_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.load_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.rpm_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.scavenging_pressure_bool_calib
import com.e.occanosidetest.utils.StaticAddress.Companion.torque_engine_bool_calib
import com.e.occanosidetest.utils.TopSpacingItemDecoration
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.main.DashboardStateEvent
import com.e.occanotestsidep.ui.main.DataStateListener
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.GaugeForCalibration
import com.e.occanotestsidep.ui.models.Ship
import kotlinx.android.synthetic.main.fragment_dash.*
import java.lang.ClassCastException
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashFragment : Fragment() ,View.OnClickListener, DashRvAdapter.Interaction{

    //maybe the problem is newInstance, the problem is maybe because the

    private var param1: String? = null
    private var param2: String? = null

    lateinit var viewModel: MainViewModel

    private var justcounter:Int = 0

    lateinit var dataStateListener: DataStateListener

    lateinit var dashRvAdapter: DashRvAdapter


    lateinit var gaugeForCalibration: GaugeForCalibration


    var ip: String? = "http://office.occano.io:4000"
    var fromCalibName: String? = ""
    var fromCalibValue:Float = 0.0f
    var currentShip = Ship()


//    private val selectedPressureUnits = false
//private var celsiusTempUnit: Boolean = false

    private var counterForSaveToLog:Int = 0
    var toastCounter:Boolean = false


    var gauge = GaugeForCalibration()

    var btnDashCurr:ImageButton? = null
    var btnDasToLog:ImageButton? = null
    var btnToStatus:ImageButton? = null


    private val TAG = "DashboardFragment"

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
    ): View? =// Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_dash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid activity")

        subscribeObservers()

        setUI(view)
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("Debug: DashFragment DataState: ${dataState} ")
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
                println("DEBUG: Setting DashFragment cyls for rv: $it")
                it?.let {
                    dashRvAdapter.submitList(it)
                }
            }
        })
    }

    private fun initRv(){
        rv_dashboard.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(0)
            addItemDecoration(topSpacingItemDecoration)
            dashRvAdapter = DashRvAdapter(this@DashFragment)
            adapter = dashRvAdapter
        }
    }

    override fun onResume() {
        super.onResume()
//        setUI(view!!)
        getPropertiesFromCalib()
    }

    fun setUI(v:View){
        initRv()
        triggerGetCylindersEvent()
        getShipPropreties()
        setPointer(v)
        setListeners()
        getAddress()
        android.util.Log.e(TAG,"set ui done")

    }

    private fun setPointer(v:View) {



        //pointers for buttons
        btnDashCurr = v.findViewById(R.id.btn_dash_current)
        btnDasToLog = v.findViewById(R.id.btn_dash_to_log)
        btnToStatus = v.findViewById(R.id.btn_dash_to_status)

        v.findViewById<TextView>(R.id.tv_current_ship_name).text = currentShip.vessel.toString()
        v.findViewById<TextView>(R.id.tv_current_ship_engine).text = currentShip.m_e.toString()

    }
    private fun setListeners(){
        btnDashCurr!!.setOnClickListener{}
        btnToStatus!!.setOnClickListener{
        }

    }




    // TODO: refactoring.
//    boolean readedPressureUnits = sharedPreferences.getBoolean("selectPressureUnit", true);  //true = bar, false = psi
//    if (readedPressureUnits != selectedPressureUnits) {
//        selectedPressureUnits = readedPressureUnits;
//        pressureFactor = selectedPressureUnits ? 1 : (float) 14.5037738;
//        pressureUnit = selectedPressureUnits ? "bar" : "psi";
//        pressureMin = selectedPressureUnits ? -3 : -30;
//        pressureMax = selectedPressureUnits ? 3 : 30;
//    }
//
//    boolean readedTempUnit = sharedPreferences.getBoolean("selectTemperatureUnit", true);  //true = celcius, false = fahrenheit
//    if (readedTempUnit != celsiusTempUnit) {
//        celsiusTempUnit = readedTempUnit;
//        temperatureUnit = getString(celsiusTempUnit ? R.string.unit_c : R.string.unit_f);
//    }
//
//    boolean readedPowerUnits = sharedPreferences.getBoolean("selectPowerUnit", true);  //true = kw, false = ps
//    if (powerUnits == null || readedPowerUnits != powerUnits) {
//        powerUnits = readedPowerUnits;
//        powerFactor = powerUnits ? 1 : 1.35962f;
//    }


    override fun onClick(v: View?) {}

    private fun triggerGetCylindersEvent() {
        viewModel.setStateEvent(DashboardStateEvent.GetCylinders())
    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }

    fun getShipPropreties(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultShipName = ""
        val defaultValue = ""
        currentShip.vessel = sharedPref.getString("nameOfCurrentShip",defaultShipName)
        currentShip.m_e = sharedPref.getString("nameOfCurrentEngine", defaultValue)

    }

    fun getPropertiesFromCalib(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultName = gauge.name
        val defaultValue = gauge.value
        fromCalibName = sharedPref.getString("nameOfGaugeFromCalib",defaultName)
        fromCalibValue = sharedPref.getFloat("valueFromCalib", defaultValue)

        when (fromCalibName){
            "torque_gauge"-> {
                torque_engine_bool_calib = false
                calib_torque_gauge = fromCalibValue
            }
            "load_gauge"-> {
                load_bool_calib = false
                calib_load_gauge = fromCalibValue
            }
            "firing_pres_gauge"-> {
                firing_pressure_bool_calib = false
                calib_firing_pres_gauge = fromCalibValue
            }
            "bmep_gauge"-> {
                bmep_bool_calib = false
                calib_bmep_gauge = fromCalibValue
            }
            "break_power_gauge"-> {
                break_power_bool_calib = false
                calib_break_power_gauge = fromCalibValue

            }
            "comp_pres_gauge"-> {
                compression_pressure_bool_calib = false
                calib_comp_pres_gauge = fromCalibValue
            }
            "engine_speed_gauge"-> {
                rpm_bool_calib = false
                calib_engine_speed_gauge = fromCalibValue
            }
            "exhaust_gauge"-> {
                exshaust_temperature_bool_calib = false
                calib_exhaust_gauge = fromCalibValue
            }
            "fuel_gauge"-> {
                fuel_flow_rate_bool_calib = false
                calib_fuel_gauge = fromCalibValue
            }
            "imep_gauge"-> {
                imep_bool_calib = false
                calib_imep_gauge = fromCalibValue
                //TODO: update the unit
            }
            "injec_gauge"-> {
                injection_timing_bool_calib = false
                calib_injec_gauge = fromCalibValue
            }
            "scave_gauge"-> {
                scavenging_pressure_bool_calib = false
                calib_scave_gauge = fromCalibValue
            }
            else -> {
                load_bool_calib = false
                calib_load_gauge = fromCalibValue
            }
        }
    }

    fun savePropertiesToCalib(gaugeForCalibration: GaugeForCalibration){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("nameOfGaugeFromdash", gaugeForCalibration.name)
            putFloat("valueOfGaugeFromdash", gaugeForCalibration.value)
            commit()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        }catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener ")
        }
    }

    override fun onItemSelected(position: Int, item: Cylinder) {
//
//        when(item){
//            R.id.torque_gauge -> {
//                gauge.name = "torque_gauge"
//                gauge.value = torqueGauge!!.speed
//
//            }
//            R.id.exhaust_gauge -> {
//                gauge.name = "exhaust_gauge"
//                gauge.value = exsahustGauge!!.speed
//            }
//            R.id.load_gauge -> {
//                gauge.name = "load_gauge"
//                gauge.value = loadGauge!!.speed
//            }
//            R.id.firing_pres_gauge -> {
//                gauge.name = "firing_pres_gauge"
//                gauge.value = firingGauge!!.speed
//            }
//            R.id.comp_pres_gauge -> {
//                gauge.name = "comp_pres_gauge"
//                gauge.value = compPresGauge!!.speed
//            }
//            R.id.scave_gauge -> {
//                gauge.name = "scave_gauge"
//                gauge.value = scaveGauge!!.speed
//            }
//            R.id.bmep_gauge -> {
//                gauge.name = "bmep_gauge"
//                gauge.value = bmepGauge!!.speed
//            }
//            R.id.break_power_gauge -> {
//                gauge.name = "break_power_gauge"
//                gauge.value = breakGauge!!.speed
//
//            }
//            R.id.engine_speed_gauge -> {
//
//                gauge.name = "engine_speed_gauge"
//                gauge.value = rpmGauge!!.speed
//
//            }
//            R.id.fuel_gauge -> {
//
//                gauge.name = "fuel_gauge"
//                gauge.value = fuelGauge!!.speed
//
//
//            }
//            R.id.imep_gauge -> {
//                gauge.name = "imep_gauge"
//                gauge.value = imepGauge!!.speed
//
//
//            }
//            R.id.injec_gauge -> {
//                gauge.name ="injec_gauge"
//                gauge.value = injecGauge!!.speed
//
//            }
//
//
//        }
//        savePropertiesToCalib(gauge)

//        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
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
        fun newInstance(sectionNumber: Int): DashFragment {
            return DashFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }
    }

}



