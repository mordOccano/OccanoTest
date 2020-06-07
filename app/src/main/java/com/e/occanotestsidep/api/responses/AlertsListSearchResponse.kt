package com.e.occanotestsidep.api.responses

import android.util.Log
import com.e.occanotestsidep.ui.models.Alert
import com.e.occanotestsidep.ui.models.Status
import com.e.occanotestsidep.ui.models.Cylinder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AlertsListSearchResponse(

    @SerializedName("alerts")
    @Expose
    var status: List<AlertSearchResponse> = ArrayList()
) {

    fun alertToListOfStatus(): List<Alert>{
        val statusList: ArrayList<Alert> = ArrayList()
        for(statusResponse in status!!){
            statusList.add(
                statusResponse.toAlert()
            )
//            Log.e("","------------------${statusList.toString()}-------------------")
        }
        return statusList
    }

    override fun toString(): String {
        return "AlertsListSearchResponse(status=$status)"
    }

}