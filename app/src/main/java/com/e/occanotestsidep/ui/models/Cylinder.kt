package com.e.occanotestsidep.ui.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "cylinders")
data class Cylinder(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cylinder_num")
    var numOfCylInEngine : Int = 0,

    @Embedded(prefix = "rpm_")
    var rpm: Gauge = Gauge(),

    @Embedded(prefix = "exhaust_temperature_")
        var exhaust_temperature: Gauge = Gauge(),

    @Embedded(prefix = "load_")
        var load: Gauge = Gauge(),

    @Embedded(prefix = "firing_pressure_")
        var firing_pressure: Gauge = Gauge(),

    @Embedded(prefix = "scavenging_pressure_")
    var scavenging_pressure: Gauge = Gauge(),

    @Embedded(prefix = "compression_pressure_")
    var compression_pressure: Gauge = Gauge(),

    @Embedded(prefix = "break_power_")
    var break_power: Gauge = Gauge(),

    @Embedded(prefix = "imep_")
    var imep: Gauge = Gauge(),

    @Embedded(prefix = "Torque_engine_")
    var torque_engine: Gauge = Gauge(),

    @Embedded(prefix = "bmep_")
    var bmep: Gauge = Gauge(),

    @Embedded(prefix = "injection_timing_")
    var injection_timing: Gauge = Gauge(),

    @Embedded(prefix = "fuel_flow_rate_")
    var fuel_flow_rate: Gauge = Gauge()
) : Parcelable {
    override fun toString(): String {
        return "Cylinder(numOfCylInEngine=${numOfCylInEngine}, rpm=$rpm, exhaust_temperature=$exhaust_temperature, load=$load, firing_pressure=$firing_pressure, scavenging_pressure=$scavenging_pressure, compression_pressure=$compression_pressure, break_power=$break_power, imep=$imep, torque_engine=$torque_engine, bmep=$bmep, injection_timing=$injection_timing, fuel_flow_rate=$fuel_flow_rate)"
    }
}