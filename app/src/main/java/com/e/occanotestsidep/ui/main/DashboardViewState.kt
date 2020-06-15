package com.e.occanotestsidep.ui.main


import com.e.occanotestsidep.ui.models.*

data class DashboardViewState(
    var cylinders: List<Cylinder>? = null,
    var statuses: List<Alert>? = null,
    var archiveStatuses: List<Alert>? = null,
    var updateResponse:String? = null,
    var graphDots: List<GraphDots>?=null
)