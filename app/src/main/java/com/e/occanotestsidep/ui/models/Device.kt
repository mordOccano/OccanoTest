package com.e.occanotestsidep.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.InetAddress

@Parcelize
data class Device(
    var ip: String? = "",
    var hostname: String? = "",
    var mac: String? = "",
    var time: Float = 0f,
    var isSelected : Boolean = false
):Parcelable {
    override fun toString(): String {
        return "Device(ip='$ip', hostname='$hostname', mac=$mac, time=$time, isSelected=$isSelected)"
    }

}

