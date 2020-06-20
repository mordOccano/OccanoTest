package com.e.occanotestsidep.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GraphDots(
    var name: Int? = 0,
    var yList:List<Double> = ArrayList<Double>()
):Parcelable {
    override fun toString(): String {
        return "GraphDots(name=$name, yList=$yList)"
    }
}

/**
data class XDots(
    var xPoint: Int? = null
)
data class YDots(
    var yPoint: Float? = null
)

 */