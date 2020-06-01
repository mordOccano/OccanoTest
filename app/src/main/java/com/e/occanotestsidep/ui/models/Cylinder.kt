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
//    //rpm
//    @ColumnInfo(name ="rpm_name")
//    var rpm_name: String = "rpm",
//
//    @ColumnInfo(name ="rpm_value")
//    var rpm_value: Float = 0f,
//
//    @ColumnInfo(name ="rpm_unit")
//    var rpm_unit: String = "Km/h",
//
//    @ColumnInfo(name ="rpm_max")
//    var rpm_max: Float = 100f,
//
//    @ColumnInfo(name ="rpm_ishealth")
//    var rpm_ishealth: Boolean = true,

    @Embedded(prefix = "rpm_")
//    @ColumnInfo(name ="rpm")
    var rpm: Gauge = Gauge(),


////exhaust_temperature
//    @ColumnInfo(name ="exhaust_temperature_name")
//    var exhaust_temperature_name: String = "exhaust_temperature",
//
//    @ColumnInfo(name ="exhaust_temperature_value")
//    var exhaust_temperature_value: Float = 0f,
//
//    @ColumnInfo(name ="exhaust_temperature_unit")
//    var exhaust_temperature_unit: String = "c°",
//
//    @ColumnInfo(name ="exhaust_temperature_max")
//    var exhaust_temperature_max: Float = 500f,
//
//    @ColumnInfo(name ="exhaust_temperature_ishealth")
//    var exhaust_temperature_ishealth: Boolean = true,
    @Embedded(prefix = "exhaust_temperature_")
//    @ColumnInfo(name ="exhaust_temperature")
        var exhaust_temperature: Gauge = Gauge(),


//  //load
//  @ColumnInfo(name ="load_name")
//  var load_name: String = "load",
//
//@ColumnInfo(name ="load_value")
//var load_value: Float = 0f,
//
//@ColumnInfo(name ="load_unit")
//var load_unit: String = "%",
//
//@ColumnInfo(name ="load_max")
//var load_max: Float = 150f,
//
//@ColumnInfo(name ="load_ishealth")
//var load_ishealth: Boolean = true,
    @Embedded(prefix = "load_")
//    @ColumnInfo(name ="load")
        var load: Gauge = Gauge(),


//@ColumnInfo(name ="firing_pressure_name")
//var firing_pressure_name: String = "load",
//
//@ColumnInfo(name ="firing_pressure_value")
//var firing_pressure_value: Float = 0f,
//
//@ColumnInfo(name ="firing_pressure_unit")
//var firing_pressure_unit: String = "bar",
//
//@ColumnInfo(name ="firing_pressure_max")
//var firing_pressure_max: Float = 300f,
//
//@ColumnInfo(name ="firing_pressure_ishealth")
//var firing_pressure_ishealth: Boolean = true,
    @Embedded(prefix = "firing_pressure_")
//    @ColumnInfo(name ="firing_pressure")
        var firing_pressure: Gauge = Gauge(),

//@ColumnInfo(name ="scavenging_pressure_name")
//var scavenging_pressure_name: String = "scavenging_pressure",
//
//@ColumnInfo(name ="scavenging_pressure_value")
//var scavenging_pressure_value: Float = 0f,
//
//@ColumnInfo(name ="scavenging_pressure_unit")
//var scavenging_pressure_unit: String = "bar",
//
//@ColumnInfo(name ="scavenging_pressure_max")
//var scavenging_pressure_max: Float = 10f,
//
//@ColumnInfo(name ="scavenging_pressure_ishealth")
//var scavenging_pressure_ishealth: Boolean = true,
    @Embedded(prefix = "scavenging_pressure_")
//    @ColumnInfo(name ="scavenging_pressure")
    var scavenging_pressure: Gauge = Gauge(),

//@ColumnInfo(name ="compression_pressure_name")
//var compression_pressure_name: String = "compression_pressure",
//
//@ColumnInfo(name ="compression_pressure_value")
//var compression_pressure_value: Float = 0f,
//
//@ColumnInfo(name ="compression_pressure_unit")
//var compression_pressure_unit: String = "bar",
//
//@ColumnInfo(name ="compression_pressure_max")
//var compression_pressure_max: Float = 300f,
//
//@ColumnInfo(name ="compression_pressure_ishealth")
//var compression_pressure_ishealth: Boolean = true,

    @Embedded(prefix = "compression_pressure_")
//    @ColumnInfo(name ="compression_pressure")
    var compression_pressure: Gauge = Gauge(),
//@ColumnInfo(name ="break_power_name")
//var break_power_name: String = "break_power",
//
//@ColumnInfo(name ="break_power_value")
//var break_power_value: Float = 0f,
//
//@ColumnInfo(name ="break_power_unit")
//var break_power_unit: String = "Kw",
//
//@ColumnInfo(name ="break_power_max")
//var break_power_max: Float = 10f,
//
//@ColumnInfo(name ="break_power_ishealth")
//var break_power_ishealth: Boolean = true,

    @Embedded(prefix = "break_power_")
//    @ColumnInfo(name ="break_power")
    var break_power: Gauge = Gauge(),

//@ColumnInfo(name ="imep_name")
//var imep_name: String = "imep",
//
//@ColumnInfo(name ="imep_value")
//var imep_value: Float = 0f,
//
//@ColumnInfo(name ="imep_unit")
//var imep_unit: String = "Kw",
//
//@ColumnInfo(name ="imep_max")
//var imep_max: Float = 30f,
//
//@ColumnInfo(name ="imep_ishealth")
//var imep_ishealth: Boolean = true,

    @Embedded(prefix = "imep_")
//    @ColumnInfo(name ="imep")
    var imep: Gauge = Gauge(),
//
//@ColumnInfo(name ="Torque_engine_name")
//var Torque_engine_name: String = "Torque_engine",
//
//@ColumnInfo(name ="Torque_engine_value")
//var Torque_engine_value: Float = 0f,
//
//@ColumnInfo(name ="Torque_engine_unit")
//var Torque_engine_unit: String = "Kn/m",
//
//@ColumnInfo(name ="Torque_engine_max")
//var Torque_engine_max: Float = 400f,
//
//@ColumnInfo(name ="Torque_engine_ishealth")
//var Torque_engine_ishealth: Boolean = true,

    @Embedded(prefix = "Torque_engine_")
//    @ColumnInfo(name ="Torque_engine")
    var torque_engine: Gauge = Gauge(),


//@ColumnInfo(name ="bmep_name")
//var bmep_name: String = "bmep",
//
//@ColumnInfo(name ="bmep_value")
//var bmep_value: Float = 0f,
//
//@ColumnInfo(name ="bmep_unit")
//var bmep_unit: String = "bar",
//
//@ColumnInfo(name ="bmep_max")
//var bmep_max: Float = 30f,
//
//@ColumnInfo(name ="bmep_ishealth")
//var bmep_ishealth: Boolean = true,


    @Embedded(prefix = "bmep_")
//    @ColumnInfo(name ="bmep")
    var bmep: Gauge = Gauge(),


//@ColumnInfo(name ="injection_timing_name")
//var injection_timing_name: String = "injection_timing",
//
//@ColumnInfo(name ="injection_timing_value")
//var injection_timing_value: Float = 0f,
//
//@ColumnInfo(name ="injection_timing_unit")
//var injection_timing_unit: String = "bar",
//
//@ColumnInfo(name ="injection_timing_max")
//var injection_timing_max: Float = 4f,
//
//@ColumnInfo(name ="injection_timing_ishealth")
//var injection_timing_ishealth: Boolean = true,

    @Embedded(prefix = "injection_timing_")
//    @ColumnInfo(name ="injection_timing")
    var injection_timing: Gauge = Gauge(),

//@ColumnInfo(name ="fuel_flow_rate_name")
//var fuel_flow_rate_name: String = "fuel_flow_rate",
//
//@ColumnInfo(name ="fuel_flow_rate_value")
//var fuel_flow_rate_value: Float = 0f,
//
//@ColumnInfo(name ="fuel_flow_rate_unit")
//var fuel_flow_rate_unit: String = "Kg/h",
//
//@ColumnInfo(name ="fuel_flow_rate_max")
//var fuel_flow_rate_max: Float = 550f,
//
//@ColumnInfo(name ="fuel_flow_rate_ishealth")
//var fuel_flow_rate_ishealth: Boolean = true


    @Embedded(prefix = "fuel_flow_rate_")
//    @ColumnInfo(name ="fuel_flow_rate")
    var fuel_flow_rate: Gauge = Gauge()
) : Parcelable {
    override fun toString(): String {
        return "Cylinder(numOfCylInEngine=${numOfCylInEngine}, rpm=$rpm, exhaust_temperature=$exhaust_temperature, load=$load, firing_pressure=$firing_pressure, scavenging_pressure=$scavenging_pressure, compression_pressure=$compression_pressure, break_power=$break_power, imep=$imep, torque_engine=$torque_engine, bmep=$bmep, injection_timing=$injection_timing, fuel_flow_rate=$fuel_flow_rate)"
    }
}