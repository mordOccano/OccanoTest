package com.e.occanotestsidep.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.e.occanotestsidep.R
import com.stealthcopter.networktools.SubnetDevices
import com.stealthcopter.networktools.SubnetDevices.OnSubnetDeviceFound
import com.stealthcopter.networktools.subnet.Device
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*

class RegisterFragment : Fragment(),View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        buttonRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
            when(v!!.id){
                R.id.buttonRegister -> {
                    findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
                }
            }
    }

    /*
    0. find navcontroller to the new fragment
    1. create ip scan fragment. in this frag will scan the ip's in the local network.
    2. then will get the results from scan as recycler view, with an option to choose one of them, and then
    3. scan the name from the qr code and match them
     */
    private fun scanIp() {
        // Asynchronously
        SubnetDevices.fromLocalAddress().findDevices(object : OnSubnetDeviceFound {
            override fun onDeviceFound(device: Device) {}
            override fun onFinished(devicesFound: ArrayList<Device>) {}
        })
    }
//    fun getAddress(){
//        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//        val defaultValue = resources.getString(R.string.default_ip)
//        val ip = sharedPref.getString("ip", defaultValue)
//    }
}