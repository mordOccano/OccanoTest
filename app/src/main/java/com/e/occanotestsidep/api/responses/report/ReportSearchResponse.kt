package com.e.occanotestsidep.api.responses.report

import android.os.Parcelable
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
    var plots: List<GraphDots>,


    @SerializedName("insights")
    @Expose
    var statuses: List<Alert>
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

    override fun toString(): String {
        return "ReportSearchResponse(cylinders=$cylinders, plots=$plots, statuses=$statuses)"
    }
}