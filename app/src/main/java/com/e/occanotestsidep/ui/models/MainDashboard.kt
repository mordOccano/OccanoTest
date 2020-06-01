package com.e.occanotestsidep.ui.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "main_dashboard_cell")
data class MainDashboard(

    @ColumnInfo(name ="name")
    var name: String ="",

    @ColumnInfo(name ="value")
    var value : Float = 0f,

    @ColumnInfo(name ="max_speed")
    var maxSpeed : Float = 0f,

    @ColumnInfo(name ="isGaugeHealth")
    var gaugeHealth : Int = 0,

    @ColumnInfo(name ="gaugeUnit")
    var unit : String = "",

    //Maybe give up on this field
    @ColumnInfo(name ="status")
    var status: List<Status>? = null

) {
    override fun toString(): String {
        return "MainDashboard(name='$name', value=$value, status=$status)"
    }
}