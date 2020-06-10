package com.e.occanotestsidep.api.responses


import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.Gauge
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CylinderSearchResponse(

    @Expose
    @SerializedName("cylinder_num")
    var numOfCylInEngine: Int = 0,
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
) {
    fun toCylinder(): Cylinder {
        return Cylinder(
            numOfCylInEngine,
            Gauge(numOfCylInEngine,"rpm",rpm.value,"rpm",100f,rpm.average,rpm.standard,rpm.getHealthyGauge()),
            Gauge(numOfCylInEngine,"exhaust_temperature",exhaust_temperature.value,"c°",500f,exhaust_temperature.average,exhaust_temperature.standard,exhaust_temperature.getHealthyGauge()),
            Gauge(numOfCylInEngine,"load",load.value,"%",150f,load.average,load.standard,load.getHealthyGauge()),
            Gauge(numOfCylInEngine,"firing_pressure",firing_pressure.value,"bar",300f,firing_pressure.average,firing_pressure.standard,firing_pressure.getHealthyGauge()),
            Gauge(numOfCylInEngine,"scavenging_pressure",scavenging_pressure.value,"bar",10f,scavenging_pressure.average,scavenging_pressure.standard,scavenging_pressure.getHealthyGauge()),
            Gauge(numOfCylInEngine,"compression_pressure",compression_pressure.value,"bar",300f,compression_pressure.average,compression_pressure.standard,compression_pressure.getHealthyGauge()),
            Gauge(numOfCylInEngine,"break_power",break_power.value/1000,"Kw",10f,break_power.average,break_power.standard,break_power.getHealthyGauge()),
            Gauge(numOfCylInEngine,"imep",imep.value,"",30f,imep.average,imep.standard,imep.getHealthyGauge()),
            Gauge(numOfCylInEngine,"torque_engine",torque_engine.value/1000,"Kn/m",400f,torque_engine.average,torque_engine.standard,torque_engine.getHealthyGauge()),
            Gauge(numOfCylInEngine,"bmep",bmep.value,"bar",30f,bmep.average,bmep.standard,bmep.getHealthyGauge()),
            Gauge(numOfCylInEngine,"injection_timing",injection_timing.value,"bar",4f,injection_timing.average,injection_timing.standard,injection_timing.getHealthyGauge()),
            Gauge(numOfCylInEngine,"fuel_flow_rate",fuel_flow_rate.value,"Kg/h",550f,fuel_flow_rate.average,fuel_flow_rate.standard,fuel_flow_rate.getHealthyGauge())
        )
    }

    override fun toString(): String {
        return "CylinderSearchResponse(numOfCylInEngine=$numOfCylInEngine, rpm=$rpm, exhaust_temperature=$exhaust_temperature, load=$load, firing_pressure=$firing_pressure, scavenging_pressure=$scavenging_pressure, compression_pressure=$compression_pressure, break_power=$break_power, imep=$imep, torque_engine=$torque_engine, bmep=$bmep, injection_timing=$injection_timing, fuel_flow_rate=$fuel_flow_rate)"
    }


}

//date_updated = DateUtils.convertServerStringDateToLong(
//                date_updated
//            ),




