package com.e.occanotestsidep.ui.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity (tableName = "gauges")
data class GaugeForCalibration(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id:Int = 0,
   @ColumnInfo(name= "gaugename")
    var name: String = "",
    @ColumnInfo(name = "gauge_value")
    var value: Float = 11F
):Parcelable

