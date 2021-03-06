package com.e.occanotestsidep.api.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlin.math.absoluteValue

data class GaugeApiResponse(
    //add value that define the difference between std per gauge, in this ctor, i mean that model class get value that define how much difference create a situation for the user.

    @Expose
    @SerializedName("value")
    var value: Float = 0.0f,

    @Expose
    @SerializedName("std")
    var standard : Float = 0.0f,

    @Expose
    @SerializedName("avg")
    var average: Float = 0.0f

) {
    fun getHealthyGauge() : Int{
        return if (standard.absoluteValue>0.3){
            when {
                standard>0.3 -> {
                    1
                }
                standard< -0.3 -> {
                    -1
                }
                else -> {
                    0
                }
            }
        }else{
            0
        }
    }

    fun toGauge(){

    }
    override fun toString(): String {
        return "GaugeApiResponse(value=$value, standard=$standard, average=$average)"
    }
}