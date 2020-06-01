package com.e.occanotestsidep.ui.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.e.occanotestsidep.utils.ListConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "CylindersForComparison")
data class CylindersForComparison(

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="rpm_gaugeList")
    var rpm_gaugeList :ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="exhaust_temperature_gaugeList")
    var exhaust_temperature_gaugeList: ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="load_gaugeList")
    var load_gaugeList: ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="firing_pressure_gaugeList")
    var firing_pressure_gaugeList: ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="scavenging_pressure_gaugeList")
    var scavenging_pressure_gaugeList: ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="compression_pressure_gaugeList")
    var compression_pressure_gaugeList: ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="break_power_gaugeList")
    var break_power_gaugeList: ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="imep_gaugeList")
    var imep_gaugeList: ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="Torque_engine_gaugeList")
    var Torque_engine_gaugeList: ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="bmep_gaugeList")
    var bmep_gaugeList: ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="injection_timing_gaugeList")
    var injection_timing_gaugeList: ArrayList<Gauge> = ArrayList(),

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name ="fuel_flow_rate_gaugeList")
    var fuel_flow_rate_gaugeList: ArrayList<Gauge> = ArrayList()
):Parcelable {
    override fun toString(): String {
        return "CylindersForComparison(rpm_gaugeList=$rpm_gaugeList, exhaust_temperature_gaugeList=$exhaust_temperature_gaugeList, load_gaugeList=$load_gaugeList, firing_pressure_gaugeList=$firing_pressure_gaugeList, scavenging_pressure_gaugeList=$scavenging_pressure_gaugeList, compression_pressure_gaugeList=$compression_pressure_gaugeList, break_power_gaugeList=$break_power_gaugeList, imep_gaugeList=$imep_gaugeList, Torque_engine_gaugeList=$Torque_engine_gaugeList, bmep_gaugeList=$bmep_gaugeList, injection_timing_gaugeList=$injection_timing_gaugeList, fuel_flow_rate_gaugeList=$fuel_flow_rate_gaugeList)"
    }
}