package com.e.occanotestsidep.ui.models

data class GraphDots(
    var name: Int? = null,
    var yList:List<Float>? = null

//    var xList:List<XDots>? = null,
//    var yList:List<YDots>? = null,
) {
    override fun toString(): String {
        return "GraphDots(name=$name, yList=$yList)"
    }
}

/**
data class XDots(
    var xPoint: Int? = null
)
data class YDots(
    var yPoint: Float? = null
)

 */