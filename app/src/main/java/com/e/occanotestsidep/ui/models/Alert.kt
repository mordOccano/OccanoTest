package com.e.occanotestsidep.ui.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "alerts")
data class Alert(
    @ColumnInfo(name ="alert_id")
    var alert_id: String = "",

    @ColumnInfo(name ="saverity")
    var saverity: Double = 0.0,

    @ColumnInfo(name ="description")
    var description: String = "",

    @ColumnInfo(name ="acknowledged")
    var acknowledged: Boolean = false,

    @Embedded(prefix = "labels_")
    var labels: AlertLabels? = null

):Parcelable {
    override fun toString(): String {
        return "Alert(alert_id='$alert_id', saverity=$saverity, description='$description', acknowledged=$acknowledged, labels=$labels)"
    }
}