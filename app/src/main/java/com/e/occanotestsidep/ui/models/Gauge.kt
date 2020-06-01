package com.e.occanotestsidep.ui.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gauge(
    @ColumnInfo(name ="cyl_num")
    var cyl_num:Int = 0,

    @ColumnInfo(name ="name")
    var name: String = "",

    @ColumnInfo(name ="value")
    var value: Float = 0f,

    @ColumnInfo(name ="unit")
    var unit: String = "",

    @ColumnInfo(name ="maxValue")
    var maxValue: Float = 0f,

    @ColumnInfo(name ="average")
    var average: Float = 0f,

    @ColumnInfo(name ="fromStandard")
    var fromStandard: Float = 0f,

    @ColumnInfo(name ="healthy")
    var healthy: Int = 0
):Parcelable {
    override fun toString(): String {
        return "Gauge(cyl_num=$cyl_num, name='$name', value=$value, unit='$unit', maxValue=$maxValue, healthy=$healthy)"
    }
}