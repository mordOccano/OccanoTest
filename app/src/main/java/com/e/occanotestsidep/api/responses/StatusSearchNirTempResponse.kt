package com.e.occanotestsidep.api.responses

import com.e.occanotestsidep.ui.models.AlertLabels
import com.e.occanotestsidep.ui.models.Status
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StatusSearchNirTempResponse(

    @Expose
    @SerializedName( "alert_id")
    private var statusId: String = "",

    @Expose
    @SerializedName( "cylinder_num")
    var cylinder_num: Int = 0,

    @Expose
    @SerializedName( "location")
     var statusMainTitle: String? = "",

    @Expose
    @SerializedName( "description")
     var statusSubTitle: String? = "",
//
//    @Expose
//    @SerializedName( "lessContent")
     var statusLessContent: String? = "",

    @Expose
    @SerializedName( "labels")
     var statusMoreContent: AlertLabels? = null,

    @Expose
    @SerializedName( "type")
     var statusKindOfDangerS:String = "",

     var statusKindOfDanger:Int = 0,

    @Expose
    @SerializedName( "acknowledged")
     var kindOfAcknowledge:Boolean = false,

//
     var timeStampOfstatus: String? = ""


) {
    fun toStatus(): Status {
        if (statusKindOfDangerS.contains("alert")){
            statusKindOfDanger = 1
        }

//        var knowledge = false
//        if(kindOfAcknowledge == 1){
//            knowledge = true
//        }

        return Status(
            statusId,
            cylinder_num,
            statusMainTitle,
            statusSubTitle,
            statusLessContent,
            statusMoreContent.toString(),
            statusKindOfDanger,
            kindOfAcknowledge,
            timeStampOfstatus
        )
    }




    override fun toString(): String {
        return "StatusSearchResponse(statusId=$statusId, statusMainTitle=$statusMainTitle, statusSubTitle=$statusSubTitle, statusLessContent=$statusLessContent, statusMoreContent=$statusMoreContent, statusKindOfDanger=$statusKindOfDanger, kindOfAcknowledge=$kindOfAcknowledge "
//                "timeStampOfstatus=$timeStampOfstatus)"
    }


}





