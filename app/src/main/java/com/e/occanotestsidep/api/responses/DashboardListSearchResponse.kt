package com.e.occanotestsidep.api.responses

import com.e.occanotestsidep.ui.models.Status
import com.e.occanotestsidep.ui.models.Cylinder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DashboardListSearchResponse(

    @SerializedName("cylinders")
    @Expose
    var cylinders: List<CylinderSearchResponse>

) {
    fun cylToList(): List<Cylinder>{
        val cylsList: ArrayList<Cylinder> = ArrayList()
        for(cylinderResponse in cylinders){
            cylsList.add(
                cylinderResponse.toCylinder()
            )
        }
        println("DashboardListSearchResponse cyl to list")
        return cylsList
    }
}