package com.e.occanotestsidep.ui.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReportForDashboard(
    @Expose
    @SerializedName("cylinder_0")
    var cylinder1 : Cylinder,
    @Expose
    @SerializedName("cylinder_1")
    var cylinder2: Cylinder,
    @Expose
    @SerializedName("cylinder_2")
    var cylinder3: Cylinder,
    @Expose
    @SerializedName("cylinder_3")
    var cylinder4: Cylinder,
    @Expose
    @SerializedName("cylinder_4")
    var cylinder5: Cylinder,
    @Expose
    @SerializedName("cylinder_5")
    var cylinder6: Cylinder,
    @Expose
    @SerializedName("cylinder_6")
    var cylinder7: Cylinder,
    @Expose
    @SerializedName("cylinder_7")
    var cylinder8: Cylinder,
    @Expose
    @SerializedName("cylinder_8")
    var cylinder9: Cylinder,
    @Expose
    @SerializedName("cylinder_9")
    var cylinder10: Cylinder,
    @Expose
    @SerializedName("cylinder_10")
    var cylinder11: Cylinder,
    @Expose
    @SerializedName("cylinder_11")
    var cylinder12: Cylinder
)