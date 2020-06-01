package com.e.occanotestsidep.ui.main.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.e.occanosidetest.utils.StaticAddress
import com.e.occanosidetest.utils.TopSpacingItemDecoration
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.CalibGauge
import com.e.occanotestsidep.utils.Single
import com.github.anastr.speedviewlib.AwesomeSpeedometer
import com.kevalpatel2106.rulerpicker.RulerValuePicker
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import kotlinx.android.synthetic.main.fragment_calibration_fragment_z.*

class CalibrationFragment :Fragment(),View.OnClickListener, CalibrationRvAdapter.Interaction{

    lateinit var rulerValuePicker: RulerValuePicker
    private var ip:String? = null
    lateinit var calibrationRvAdapter: CalibrationRvAdapter
    private var tempList:ArrayList<CalibGauge> = ArrayList()
    private var rvList:ArrayList<CalibGauge> = ArrayList()
    private lateinit var currentTemp:CalibGauge

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calibration_fragment_z,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentTemp = CalibGauge()

        //TODO("do it properly, first the request as much as possible")
        setListeners()
        getAddress()
        initUI()
        litenToRule()
        initRV()
        initFakeRvList()
    }

    private fun initRV() {
        rv_calibration.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            calibrationRvAdapter = CalibrationRvAdapter(this@CalibrationFragment)
            adapter = calibrationRvAdapter
        }
    }

        private fun initFakeRvList() {
            for(i in 1..12){
                rvList.add(CalibGauge())
            }
            rvList[6] = CalibGauge("rpm")
            rvList[7] = CalibGauge("exhaust_temperature")
            rvList[1] = CalibGauge("load")
            rvList[2] = CalibGauge("firing_pressure")
            rvList[11] = CalibGauge("scavenging_pressure")
            rvList[5] = CalibGauge("compression_pressure")
            rvList[4] = CalibGauge("break_power")
            rvList[9] = CalibGauge("imep")
            rvList[0] = CalibGauge("torque_engine")
            rvList[3] = CalibGauge("bmep")
            rvList[10] = CalibGauge("injection_timing")
            rvList[8] = CalibGauge("fuel_flow_rate")

            calibrationRvAdapter.submitList(rvList)
    }


    override fun onResume() {
        super.onResume()
        initUI()
//        setGauges()
    }

    private fun setListeners() {
        requireView().findViewById<ImageButton>(R.id.send_data_to_calibration).setOnClickListener(this)
        requireView().findViewById<ImageButton>(R.id.send_data_to_calibration).isClickable = false
        }



    private fun initUI(){
        rulerValuePicker = requireView().findViewById(R.id.height_ruler_picker)
        rulerValuePicker.setMinMaxValue(0,100)
        requireView().findViewById<TextView>(R.id.tv_num_data_to_calibrate).text = currentTemp?.value.toString()
        requireView().findViewById<AwesomeSpeedometer>(R.id.calib_gauge).speedTo(tv_num_data_to_calibrate.text.toString().toFloat())

    }

    private fun litenToRule() {
        height_ruler_picker.setValuePickerListener(object : RulerValuePickerListener {
            override fun onValueChange(value: Int) {
                tv_num_data_to_calibrate.text = value.toString()
                calib_gauge.speedTo(value.toFloat())
                send_data_to_calibration.isClickable = true
            }

            override fun onIntermediateValueChange(selectedValue: Int) {
                //Value changed but the user is still scrolling the ruler.
                //This value is not final value. You can utilize this value to display the current selected value.
                send_data_to_calibration.isClickable = false
                tv_num_data_to_calibrate.text = selectedValue.toString()
                calib_gauge.speedTo(selectedValue.toFloat())
            }
        })
    }

//

    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.send_data_to_calibration -> {
                sendDataForCalib()
                Toast.makeText(this.context,"manual correction sent", Toast.LENGTH_SHORT).show()
//
//                if (findNavController().currentDestination?.id == R.id.calibrationFragmentZ) {
//                    findNavController().navigate(R.id.action_calibrationFragmentZ_to_dashFragment)}
//                else if (findNavController().currentDestination?.id==R.id.subDadhboardContainer){
//                    findNavController().navigate(R.id.action_subDadhboardContainer_to_dashFragment)
                }

            else-> Toast.makeText(this.context,"clicked", Toast.LENGTH_SHORT).show()

        }
    }

    private fun sendDataForCalib(){
        val requestUrl = "http://"+ip+"/manual_correction/"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            requestUrl,
            Response.Listener { _ ->
                //                    Log.e(
//                        "Volley Result",
//                        "" + response
//                    ) //the response contains the result from the server, a json string or any other object returned by your server
            },
            Response.ErrorListener { error ->
                error.printStackTrace() //log the error resulting from the request for diagnosis/debugging
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val postMap: MutableMap<String, String> = HashMap()
                postMap["viewed_field"] = currentTemp?.name
                postMap["viewed_value"] = currentTemp?.value.toString()
                postMap["manual_corrected_value"] = tv_num_data_to_calibrate.text.toString()
//                Toast.makeText(context,postMap.toString(),Toast.LENGTH_SHORT).show()
                return postMap
            }
        }
        Single.getInstance(this.context).addToRequestQueue(stringRequest)
    }

    private fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }


    override fun onItemSelected(position: Int, item: CalibGauge) {
        for (tempItem in tempList){
            tempItem.isSelected = false
        }
        item.isSelected = true
        calibrationRvAdapter.notifyDataSetChanged()

        currentTemp = item

        updateGauge(position)
    }

    private fun updateGauge(position: Int) {
        //get num from rv and decide the value for the gauge by the exist method
        when (position){
            0-> {
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_torque_gauge.toInt())
                //TODO: change to the average from viewmodel

                rulerValuePicker.selectValue(0)
                calib_d_gauge_name.text = "torque"
                calib_gauge.setUnit("KN/m")
                calib_gauge.setMaxSpeed(StaticAddress.max_torque_gauge)
//                return
            }
            1-> {
                calib_d_gauge_name.text = "load"
                calib_gauge.setUnit("%")
                calib_gauge.setMaxSpeed(StaticAddress.max_load_gauge)
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_load_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return
            }
           2-> {
                calib_d_gauge_name.text = "firing pressure"
                calib_gauge.setUnit("bar")
                calib_gauge.setMaxSpeed(StaticAddress.max_firing_pres_gauge)
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_firing_pres_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return

            }
           3-> {
                calib_d_gauge_name.text = "bmep"
                calib_gauge.setUnit("bar")
                calib_gauge.setMaxSpeed(StaticAddress.max_bmep_gauge)
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_bmep_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return

            }
            4-> {
                calib_d_gauge_name.text = "break power"
                calib_gauge.setUnit("Kw")
                calib_gauge.setMaxSpeed(StaticAddress.max_break_power_gauge)
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_break_power_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return

            }
            5-> {
                calib_d_gauge_name.text = "comppression presssure"
                calib_gauge.setUnit("bar")
                calib_gauge.setMaxSpeed(StaticAddress.max_comp_pres_gauge)
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_comp_pres_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return

            }
            6-> {
                calib_d_gauge_name.text = "engine speed"
                calib_gauge.setUnit("Rpm")
                calib_gauge.setMaxSpeed(StaticAddress.max_engine_speed_gauge)
                //
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_engine_speed_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return


            }
            7-> {
                calib_d_gauge_name.text = "exhaust"
                calib_gauge.setUnit("C°")
                calib_gauge.setMaxSpeed(StaticAddress.max_exhaust_gauge)
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_exhaust_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return


            }
            8 -> {
                calib_d_gauge_name.text = "fuel"
                calib_gauge.setUnit("Kg/h")
                calib_gauge.setMaxSpeed(StaticAddress.max_fuel_gauge)
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_fuel_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return


            }
            9-> {
                calib_d_gauge_name.text = "imep"
                calib_gauge.setUnit("")
                calib_gauge.setMaxSpeed(StaticAddress.max_imep_gauge)
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_imep_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return


                //TODO: update the unit
            }
            10-> {
                calib_d_gauge_name.text = "injec"
                calib_gauge.setUnit("bar")
                calib_gauge.setMaxSpeed(StaticAddress.max_injec_gauge)
                calib_gauge.trembleDegree = 0.01f
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_injec_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return


            }
            11-> {
                calib_d_gauge_name.text = "scave"
                calib_gauge.setUnit("bar")
                calib_gauge.setMaxSpeed(StaticAddress.max_scave_gauge)
                rulerValuePicker.setMinMaxValue(0, StaticAddress.max_scave_gauge.toInt())
                rulerValuePicker.selectValue(0)
//                return


            }
            else -> {
                calib_d_gauge_name.text = this.currentTemp?.name
                calib_gauge.setUnit("")
                calib_gauge.setMaxSpeed(this.currentTemp?.value?.times(2)!!)
                rulerValuePicker.setMinMaxValue(0,(this.currentTemp?.value?.times(2)!!).toInt())
                rulerValuePicker.selectValue(0)
//                return


            }

        }
        calib_gauge.tickNumber = 9
    }


}