package com.e.occanotestsidep.api.responses.report

import android.os.Parcelable
import com.e.occanotestsidep.api.responses.GaugeApiResponse
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.Gauge
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RDashboard (

    @Expose
    @SerializedName("engine_speed_mean")
    var rpm: GaugeApiResponse,

    @Expose
    @SerializedName("exhaust_temperature")
    var exhaust_temperature: GaugeApiResponse,

    @Expose
    @SerializedName("load")
    var load: GaugeApiResponse,

    @Expose
    @SerializedName("firing_pressure")
    var firing_pressure: GaugeApiResponse,

    @Expose
    @SerializedName("scavenging_pressure")
    var scavenging_pressure: GaugeApiResponse,

    @Expose
    @SerializedName("compression_pressure")
    var compression_pressure: GaugeApiResponse,

    @Expose
    @SerializedName("break_power")
    var break_power: GaugeApiResponse,

    @Expose
    @SerializedName("imep")
    var imep: GaugeApiResponse,

    @Expose
    @SerializedName("Torque_engine")
    var torque_engine: GaugeApiResponse,

    @Expose
    @SerializedName("bmep")
    var bmep: GaugeApiResponse,

    @Expose
    @SerializedName("injection_timing")
    var injection_timing: GaugeApiResponse,

    @Expose
    @SerializedName("fuel_flow_rate")
    var fuel_flow_rate: GaugeApiResponse
):Parcelable {

    override fun toString(): String {
        return "CylinderSearchResponse (rpm=$rpm, exhaust_temperature=$exhaust_temperature, load=$load, firing_pressure=$firing_pressure, scavenging_pressure=$scavenging_pressure, compression_pressure=$compression_pressure, break_power=$break_power, imep=$imep, torque_engine=$torque_engine, bmep=$bmep, injection_timing=$injection_timing, fuel_flow_rate=$fuel_flow_rate)"
    }


}
