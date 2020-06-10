package com.e.occanosidetest.utils

import android.app.Activity
import android.content.Context
import com.e.occanotestsidep.R

class StaticAddress {
    companion object {
//        var Ip = "192.168.43.177"
//            @JvmStatic
//            lateinit var Ip: String
//            @JvmStatic var URL_LOCAL_SHIP_REPORT : String
//            @JvmStatic var URL_LOCAL_SHIP_CALIBRATION : String
//            @JvmStatic var URL_LOCAL_SHIP_REGISTER : String
//
//
//            init {
//                     Ip = "office.occano.io:4000"
//
//
//                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//                    val defaultValue = "office.occano.io:4000"
//                    val Ip = sharedPref.getString("ip", defaultValue)
//
//
//             URL_LOCAL_SHIP_REPORT = "http://"+Ip+"/report/"
//             URL_LOCAL_SHIP_CALIBRATION = "http://"+Ip+"/manual_correction/"
//             URL_LOCAL_SHIP_REGISTER = "http://"+Ip+"/register/"
//
//            }
//

//            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//            with (sharedPref.edit()) {
//                    putInt(getString(R.string.saved_high_score_key), newHighScore)
//                    commit()
//            }
//
//

        const val NETWORK_TIMEOUT = 6000L
        const val CACHE_TIMEOUT = 2000L

        //TODO: set to shared preference
        //max value gauge
        var max_torque_gauge = 400f
        var max_bmep_gauge = 30f
        var max_break_power_gauge = 100000f
        var max_comp_pres_gauge = 300f
        var max_engine_speed_gauge = 100f
        var max_exhaust_gauge = 500f
        var max_firing_pres_gauge = 300f
        var max_fuel_gauge = 550f
        var max_imep_gauge = 30f
        var max_injec_gauge = 4f
        var max_load_gauge = 150f
        var max_scave_gauge = 10f


//        var calib_torque_gauge = 0f
//        var calib_bmep_gauge = 0f
//        var calib_break_power_gauge = 0f
//        var calib_comp_pres_gauge = 0f
//        var calib_engine_speed_gauge = 0f
//        var calib_exhaust_gauge =  0f
//        var calib_firing_pres_gauge = 0f
//        var calib_fuel_gauge =  0f
//        var calib_imep_gauge = 0f
//        var calib_injec_gauge = 0f
//        var calib_load_gauge =  0f
//        var calib_scave_gauge = 0f

        var calib_torque_gauge = 0f
        var calib_bmep_gauge = 0f
        var calib_break_power_gauge = 0f
        var calib_comp_pres_gauge = 0f
        var calib_engine_speed_gauge = 0f
        var calib_exhaust_gauge = 0f
        var calib_firing_pres_gauge = 0f
        var calib_fuel_gauge = 0f
        var calib_imep_gauge = 0f
        var calib_injec_gauge = 0f
        var calib_load_gauge = 0f
        var calib_scave_gauge = 0f

        var rpm_bool_calib: Boolean? = true
        var exshaust_temperature_bool_calib: Boolean? = true
        var load_bool_calib: Boolean? = true
        var firing_pressure_bool_calib: Boolean? = true
        var scavenging_pressure_bool_calib: Boolean? = true
        var compression_pressure_bool_calib: Boolean? = true
        var break_power_bool_calib: Boolean? = true
        var imep_bool_calib: Boolean? = true
        var torque_engine_bool_calib: Boolean? = true
        var bmep_bool_calib: Boolean? = true
        var injection_timing_bool_calib: Boolean? = true
        var fuel_flow_rate_bool_calib: Boolean? = true

        var local_mics_recv_current: String = ""
        var local_mics_recv_previous: String = ""
        var local_ir_recv_current: String = ""
        var local_ir_recv_tester_previous: String = ""

        var bool_mics_recv: Boolean = true
        var bool_ir_recv: Boolean = true

        var counter_compare_to_local: Int = 0
        var sec_bool_counter_compare_to_local: Boolean = false

        const val TESTING_NETWORK_DELAY = 0L
        const val TESTING_CACHE_DELAY = 1000L

        const val UNABLE_TODO_OPERATION_WO_INTERNET =
            "Can't do that operation without an internet connection"

        const val ERROR_SAVE_ACCOUNT_PROPERTIES =
            "Error saving account properties.\nTry restarting the app."
        const val ERROR_SAVE_AUTH_TOKEN =
            "Error saving authentication token.\nTry restarting the app."
        const val ERROR_SOMETHING_WRONG_WITH_IMAGE = "Something went wrong with the image."
        const val ERROR_MUST_SELECT_IMAGE = "You must select an image."

        const val GENERIC_AUTH_ERROR = "Error"
        const val INVALID_PAGE = "Invalid page."
        const val ERROR_CHECK_NETWORK_CONNECTION = "Check network connection."
        const val ERROR_UNKNOWN = "Unknown error"
        const val INVALID_CREDENTIALS = "Invalid credentials"
        const val SOMETHING_WRONG_WITH_IMAGE = "Something went wrong with the image."
        const val INVALID_STATE_EVENT = "Invalid state event"
        const val CANNOT_BE_UNDONE = "This can't be undone."
        const val NETWORK_ERROR = "Network error"
        const val NETWORK_ERROR_TIMEOUT = "Network timeout"
        const val CACHE_ERROR_TIMEOUT = "Cache timeout"
        const val UNKNOWN_ERROR = "Unknown error"


        const val UNABLE_TO_RESOLVE_HOST = "Unable to resolve host"


        fun isNetworkError(msg: String): Boolean {
            when {
                msg.contains(UNABLE_TO_RESOLVE_HOST) -> return true
                else -> return false
            }
        }

        fun isPaginationDone(errorResponse: String?): Boolean {
            // if error response = '{"detail":"Invalid page."}' then pagination is finished
            return errorResponse?.contains(INVALID_PAGE) ?: false
        }

    }

}

//for wherever korti
//        fun URL_LOCAL_SHIP_REPORT() : String = "http://"+Ip+":4001/report/"
//        fun URL_LOCAL_SHIP_CALIBRATION() : String = "http://"+Ip+":4001/manual_correction/"
//        fun URL_LOCAL_SHIP_REGISTER() : String = "http://"+Ip+":4001/register/"

//        fun URL_LOCAL_SHIP_REPORT() : String = "http://"+Ip+"/report/"
//        fun URL_LOCAL_SHIP_CALIBRATION() : String = "http://"+Ip+"/manual_correction/"
//        fun URL_LOCAL_SHIP_REGISTER() : String = "http://"+Ip+"/register/"
