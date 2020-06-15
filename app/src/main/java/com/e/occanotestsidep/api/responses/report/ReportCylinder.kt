package com.e.occanotestsidep.api.responses.report

import android.os.Parcelable
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.Gauge
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ReportCylinder (

    @SerializedName("dashboard")
    @Expose
    var rDashboard: RDashboard,

    @SerializedName("cylinder_num")
    @Expose
    var cylinder_num: Int
):Parcelable{
    fun toCylinder(): Cylinder {
        return Cylinder(
            cylinder_num,
            Gauge(cylinder_num,"rpm",rDashboard.rpm.value,"rpm",100f,rDashboard.rpm.average,rDashboard.rpm.standard,rDashboard.rpm.getHealthyGauge()),
            Gauge(cylinder_num,"exhaust_temperature",rDashboard.exhaust_temperature.value,"c°",500f,rDashboard.exhaust_temperature.average,rDashboard.exhaust_temperature.standard,rDashboard.exhaust_temperature.getHealthyGauge()),
            Gauge(cylinder_num,"load",rDashboard.load.value,"%",150f,rDashboard.load.average,rDashboard.load.standard,rDashboard.load.getHealthyGauge()),
            Gauge(cylinder_num,"firing_pressure",rDashboard.firing_pressure.value,"bar",300f,rDashboard.firing_pressure.average,rDashboard.firing_pressure.standard,rDashboard.firing_pressure.getHealthyGauge()),
            Gauge(cylinder_num,"scavenging_pressure",rDashboard.scavenging_pressure.value,"bar",10f,rDashboard.scavenging_pressure.average,rDashboard.scavenging_pressure.standard,rDashboard.scavenging_pressure.getHealthyGauge()),
            Gauge(cylinder_num,"compression_pressure",rDashboard.compression_pressure.value,"bar",300f,rDashboard.compression_pressure.average,rDashboard.compression_pressure.standard,rDashboard.compression_pressure.getHealthyGauge()),
            Gauge(cylinder_num,"break_power",rDashboard.break_power.value/1000,"Kw",10f,rDashboard.break_power.average,rDashboard.break_power.standard,rDashboard.break_power.getHealthyGauge()),
            Gauge(cylinder_num,"imep",rDashboard.imep.value,"",30f,rDashboard.imep.average,rDashboard.imep.standard,rDashboard.imep.getHealthyGauge()),
            Gauge(cylinder_num,"torque_engine",rDashboard.torque_engine.value/1000,"Kn/m",400f,rDashboard.torque_engine.average,rDashboard.torque_engine.standard,rDashboard.torque_engine.getHealthyGauge()),
            Gauge(cylinder_num,"bmep",rDashboard.bmep.value,"bar",30f,rDashboard.bmep.average,rDashboard.bmep.standard,rDashboard.bmep.getHealthyGauge()),
            Gauge(cylinder_num,"injection_timing",rDashboard.injection_timing.value,"bar",4f,rDashboard.injection_timing.average,rDashboard.injection_timing.standard,rDashboard.injection_timing.getHealthyGauge()),
            Gauge(cylinder_num,"fuel_flow_rate",rDashboard.fuel_flow_rate.value,"Kg/h",550f,rDashboard.fuel_flow_rate.average,rDashboard.fuel_flow_rate.standard,rDashboard.fuel_flow_rate.getHealthyGauge())
        )

    }

}