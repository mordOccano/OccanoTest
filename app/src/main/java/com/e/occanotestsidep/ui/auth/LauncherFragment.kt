package com.e.occanotestsidep.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.e.occanosidetest.utils.StaticAddress
import com.e.occanotestsidep.R
import kotlinx.android.synthetic.main.fragment_launcher.*

class LauncherFragment:Fragment(), View.OnClickListener{
    lateinit var navController: NavController
     var ip:String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launcher,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        getAddress()
        setListeners(view)


    }

    private fun setListeners(view: View) {
        view.findViewById<ImageButton>(R.id.start_btn).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_set_ip).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_open_set_ip).setOnClickListener(this)
        view.findViewById<View>(R.id.focusable_view).requestFocus()
        view.findViewById<TextView>(R.id.tv_get_ip).text = "the current base ip is: $ip"
        view.findViewById<ImageButton>(R.id.btn_launcher_to_scan_ip).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.start_btn -> {
                start_btn.isClickable = false
                findNavController().navigate(R.id.action_launcherFragment_to_settingsFragment)
            }
            R.id.btn_open_set_ip -> {
                tv_get_ip.visibility = View.GONE
                et_set_ip.visibility = View.VISIBLE
                btn_open_set_ip.visibility = View.GONE
                btn_set_ip.visibility = View.VISIBLE
            }
            R.id.btn_set_ip -> {
                btn_set_ip.isClickable = false
                start_btn.isClickable = false
                if (et_set_ip.text!=null){
                    saveAddressProperties()
                    et_set_ip.visibility = View.GONE
                    tv_get_ip.text = "the current base ip is: ${ip}"
                    tv_get_ip.visibility = View.VISIBLE
                    start_btn.isClickable = true
                }
            }
            R.id.btn_launcher_to_scan_ip ->{
                btn_launcher_to_scan_ip.isClickable = false
                findNavController().navigate(R.id.action_launcherFragment_to_scanIpFragment)
                btn_launcher_to_scan_ip.isClickable = true
            }
            else -> {
                btn_set_ip.isClickable = false
            }
        }
    }

    fun saveAddressProperties(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString("ip", et_set_ip.text.toString())
                    commit()
            }

        getAddress()
    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }

}