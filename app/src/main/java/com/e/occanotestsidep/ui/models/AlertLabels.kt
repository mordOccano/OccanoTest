package com.e.occanotestsidep.ui.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlertLabels(

    @Expose
    @SerializedName("average_value")
    var average_value : Float = 0f,

    @Expose
    @SerializedName("gauge_name")
    var gauge_name : String = "",

    @Expose
    @SerializedName("gauge_value")
    var gauge_value : Float = 0f
):Parcelable {
    override fun toString(): String {
        return "gauge name='$gauge_name', gauge_value=$gauge_value, average value = $average_value "
    }
}