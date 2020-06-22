package com.e.occanotestsidep

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.e.occanotestsidep.ui.main.DataStateListener
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.utils.DataState
import kotlinx.android.synthetic.main.activity_main.*
import java.util.prefs.PreferenceChangeListener


class MainActivity : AppCompatActivity(),DataStateListener{

    var tempIp: String = ""
    var ip: String = ""
    get() {
       return getIpAddress()
    }

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        getIpAddress()
    }

    fun getIpAddress(): String{
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences?.apply {
            tempIp = getString("ip","office.occano.io")!!
        }
        return tempIp
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            //handle Loading
            showProgressBar(dataState.loading)

            //handle message
           dataState.message?.let { event ->
               event.getContentIfNotHandled()?.let {message ->
                   showToast(message)
               }
           }
        }
    }

    fun showToast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    fun showProgressBar(isVisible:Boolean){
        if (isVisible){
            progress_bar.visibility = View.VISIBLE
        }else{
            progress_bar.visibility = View.INVISIBLE
        }
    }
}
