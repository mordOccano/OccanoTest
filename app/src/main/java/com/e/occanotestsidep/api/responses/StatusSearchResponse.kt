package com.e.occanotestsidep.api.responses

import com.e.occanotestsidep.ui.models.Status
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StatusSearchResponse(

    @Expose
    @SerializedName( "statusId")
    private var statusId: Int = 0,

    @Expose
    @SerializedName( "cylinder_num")
    var cylinder_num: Int = 0,

    @Expose
    @SerializedName( "mainTitle")
     var statusMainTitle: String? = "",

    @Expose
    @SerializedName( "subTitle")
     var statusSubTitle: String? = "",

    @Expose
    @SerializedName( "lessContent")
     var statusLessContent: String? = "",

    @Expose
    @SerializedName( "moreContent")
     var statusMoreContent: String? = "",

    @Expose
    @SerializedName( "KindOfDanger")
     var statusKindOfDanger:Int = 0,

    @Expose
    @SerializedName( "kindOfAcknowledge")
     var kindOfAcknowledge:Int = 0,

    @Expose
    @SerializedName( "timeStampOfStatus")
     var timeStampOfstatus: String? = ""


) {
    fun toStatus(): Status {

        var knowledge = false
        if(kindOfAcknowledge == 1){
            knowledge = true
        }

        return Status(
            statusId,
            cylinder_num,
            statusMainTitle,
            statusSubTitle,
            statusLessContent,
            statusMoreContent,
            statusKindOfDanger,
            knowledge,
            timeStampOfstatus
        )
    }




    override fun toString(): String {
        return "StatusSearchResponse(statusId=$statusId, statusMainTitle=$statusMainTitle, statusSubTitle=$statusSubTitle, statusLessContent=$statusLessContent, statusMoreContent=$statusMoreContent, statusKindOfDanger=$statusKindOfDanger, kindOfAcknowledge=$kindOfAcknowledge, timeStampOfstatus=$timeStampOfstatus)"
    }


}





