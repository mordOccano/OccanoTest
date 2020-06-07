package com.e.occanotestsidep.api.responses

import com.e.occanotestsidep.ui.models.GraphDots
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GraphListSearchResponse (
    var graphDotsList:List<GraphSearchResponse>
){
   fun graphDataToList():List<GraphDots>{
       val graphList: ArrayList<GraphDots> = ArrayList()
       for (graphResponse in graphDotsList){
           graphList.add(
               graphResponse.toGraphDots()
           )
       }
       return graphList
   }
}

class GraphSearchResponse (
    @Expose
    @SerializedName( "cylinder_num")
    var cylinder_num: Int = 0,

    @Expose
    @SerializedName( "pressure/angle")
    var graphDots: List<Float>
){
    fun toGraphDots():GraphDots{
        return GraphDots(
            cylinder_num,
            graphDots
        )
    }
}
