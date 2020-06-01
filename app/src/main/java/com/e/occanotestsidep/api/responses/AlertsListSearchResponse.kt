package com.e.occanotestsidep.api.responses

import com.e.occanotestsidep.ui.models.Status
import com.e.occanotestsidep.ui.models.Cylinder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AlertsListSearchResponse(
//    @SerializedName("status")
//    @Expose
    var status: List<StatusSearchNirTempResponse>
) {

    fun statusToList(): List<Status>{
        val statusList: ArrayList<Status> = ArrayList()
        for(statusResponse in status){
            statusList.add(
                statusResponse.toStatus()
            )
        }
        println("status to list")
        return statusList
    }

    override fun toString(): String {
        return "AlertsListSearchResponse(status=$status)"
    }

}