package com.e.occanotestsidep.api.responses.report

import android.os.Parcelable
import com.e.occanotestsidep.ui.models.GraphDots
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RGraphDotsSearchResponse (
    @Expose
    @SerializedName("cylinder_num")
    var graph_cylinder_num:Int = 0,

    @Expose
    @SerializedName("pressure/angle")
    var pressure_angle : List<Double> = ArrayList()
):Parcelable {

    fun toGraphDots():GraphDots{
        return GraphDots(
            graph_cylinder_num,
            pressure_angle
        )
    }
    override fun toString(): String {
        return "RGraphDotsSearchResponse(graph_cylinder_num = $graph_cylinder_num, pressure_angle = $pressure_angle)"
    }
}