package com.e.occanotestsidep.api.responses.report

import android.os.Parcelable
import com.e.occanotestsidep.api.responses.AlertSearchResponse
import com.e.occanotestsidep.api.responses.AlertsListSearchResponse
import com.e.occanotestsidep.ui.models.Alert
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.GraphDots
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ReportSearchResponse (
    @SerializedName("cylinders")
    @Expose
    var cylinders: List<ReportCylinder>,


    @SerializedName("plots")
    @Expose
    var plots: List<RGraphDotsSearchResponse>,


    @SerializedName("insights")
    @Expose
    var statuses: List<AlertSearchResponse>
    ):Parcelable {

    fun toCylinderRList(): List<Cylinder>{
        val cylList: ArrayList<Cylinder> = ArrayList()
        for(response in cylinders){
            cylList.add(
                response.toCylinder()
            )
        }
        return cylList
    }

    fun ToAlertsRList(): List<Alert>{
        val statusList: ArrayList<Alert> = ArrayList()
        for(statusResponse in statuses!!){
            statusList.add(
                statusResponse.toAlert()
            )
        }
        return statusList
    }

    fun ToPlotsRList(): List<GraphDots>{
        val plotsList: ArrayList<GraphDots> = ArrayList()
        for(plotsResponse in plots!!){
            plotsList.add(
                plotsResponse.toGraphDots()
            )
        }
        return plotsList
    }


    override fun toString(): String {
        return "ReportSearchResponse(cylinders=$cylinders, plots=$plots, statuses=$statuses)"
    }
}