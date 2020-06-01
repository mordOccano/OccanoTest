package com.e.occanotestsidep.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.occanosidetest.utils.TopSpacingItemDecoration
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.main.DataStateListener
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.ui.models.Device
import com.stealthcopter.networktools.SubnetDevices
import com.stealthcopter.networktools.SubnetDevices.OnSubnetDeviceFound
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_scan_ip.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ScanIpFragment : Fragment() , View.OnClickListener , ScanIpAdapter.Interaction{

    private val TAG: String = "ScanIpFragment"
    var ip:String? = ""
    var device:Device? = null

    //needed?
    lateinit var viewModel: MainViewModel

    var resultList : ArrayList<Device> = ArrayList()
    var resultRawList : ArrayList<com.stealthcopter.networktools.subnet.Device> = ArrayList()
    lateinit var dataStateListener: DataStateListener

    lateinit var scanIpAdapter: ScanIpAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan_ip, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid activity")
//        triggerGetStatusesEvent()
        setListeners(view)
//        subscribeObservers()

        initRV()
        scanIp()
        showScanProgressBar(true)

    }

    private fun submitToRv() {
        scanIpAdapter.submitList(resultList.toList())
    }

    private fun initRV() {
        rv_scan_ip.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            scanIpAdapter = ScanIpAdapter(this@ScanIpFragment)
            adapter = scanIpAdapter
        }
    }

    private fun setListeners(view: View){
        view.findViewById<ImageButton>(R.id.btn_scan_ip)!!.setOnClickListener(this)

    }
    /*
    TODO:("
    0. find navcontroller to the new fragment
    1. create ip scan fragment. in this frag will scan the ip's in the local network.
    2. then will get the results from scan as recycler view, with an option to choose one of them, and then
    3. scan the name from the qr code and match them
    ")
     */
    private fun scanIp() {
        // Asynchronously
        SubnetDevices.fromLocalAddress().findDevices(object : OnSubnetDeviceFound {

            override fun onFinished(devicesFound: java.util.ArrayList<com.stealthcopter.networktools.subnet.Device>?) {
                Log.e(TAG, devicesFound.toString())
                GlobalScope.launch{
                    addToList(devicesFound)
                }
            }

            override fun onDeviceFound(device: com.stealthcopter.networktools.subnet.Device?) {
            }
        })
        prepareList(resultRawList)
    }

    private suspend fun addToList(devicesFound: java.util.ArrayList<com.stealthcopter.networktools.subnet.Device>?) {
        withContext(Main){
            devicesFound?.let {
                for (i in it){
                    resultList.add(Device(i.ip,i.hostname,i.mac,i.time))
                }
                Log.e(TAG,"addToList resultList: $resultList")
                        showScanProgressBar(false)

                submitToRv()

            }

        }
    }

    private fun prepareList(list: ArrayList<com.stealthcopter.networktools.subnet.Device>) {

       for (i in list){
           resultList.add(Device(i.ip,i.hostname,i.mac,i.time))
       }
        submitToRv()
        scanIpAdapter.notifyDataSetChanged()
//        showProgressBar(false)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_scan_ip ->{
                saveAddressProperties(device!!)
                findNavController().navigate(R.id.action_scanIpFragment_to_launcherFragment)
            Log.e(TAG,"$resultList")
            }
        }
    }

    override fun onItemSelected(position: Int, item: Device) {
        for (tempDevice: Device in resultList){
            tempDevice.isSelected = false
        }
        item.isSelected = true
        scanIpAdapter.notifyDataSetChanged()

        device = item
        //TODO:"alert dialog that prove the selection is right"
    }

    private fun saveAddressProperties(item: Device){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("ip", item.ip)
            commit()
        }
        getAddress()
    }

    private fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue).toString()
    }

    private fun showScanProgressBar(isVisible:Boolean){
        if (isVisible){
            progress_bar_scan?.visibility = View.VISIBLE
        }else{
            progress_bar_scan?.visibility = View.INVISIBLE
        }
    }

}
