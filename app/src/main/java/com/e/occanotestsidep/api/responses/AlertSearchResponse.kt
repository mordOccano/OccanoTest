package com.e.occanotestsidep.api.responses

import android.os.Parcelable
import com.e.occanotestsidep.ui.models.Alert
import com.e.occanotestsidep.ui.models.AlertLabels
import com.e.occanotestsidep.ui.models.Status
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class AlertSearchResponse(

    @Expose
    @SerializedName("severity")
    var saverity: Double = 0.0,

    @Expose
    @SerializedName("description")
    var description: String = "",

    @Expose
    @SerializedName("labels")
    var labels: AlertLabels? = null,

    @Expose
    @SerializedName("acknowledged")
    var acknowledged: Boolean = false,

    @Expose
    @SerializedName("alert_id")
    var alert_id: String = ""
):Parcelable {
    fun toAlert():Alert{
        return Alert(
            alert_id,
            saverity,
            description,
            acknowledged,
            labels!!
        )
    }

    override fun toString(): String {
        return "AlertSearchResponse(saverity=$saverity, description=$description, labels=$labels, alert_id='$alert_id')"
    }


}