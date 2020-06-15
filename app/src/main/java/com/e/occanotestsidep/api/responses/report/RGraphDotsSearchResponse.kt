package com.e.occanotestsidep.api.responses.report

import com.e.occanotestsidep.ui.models.GraphDots
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RGraphDotsSearchResponse (
    @Expose
    @SerializedName("cylinder_num")
    var graph_cylinder_num:Int?= null,

    @Expose
    @SerializedName("pressure/angle")
    var pressure_angle : List<GraphDots>
)