package com.e.occanotestsidep.ui.models

data class MainDataDashboard(
    var cylinders: List<Cylinder>? = null,
    var statuses: List<Alert>? = null,
    var archiveStatuses: List<Alert>? = null,
    var updateResponse:String? = null,
    var graphDots: List<GraphDots>?=null,
    var metadata: DashMetaData?= null
) {
    override fun toString(): String {
        return "MainDataDashboard(cylinders=$cylinders, statuses=$statuses, archiveStatuses=$archiveStatuses, updateResponse=$updateResponse, graphDots=$graphDots, metadata=$metadata)"
    }
}