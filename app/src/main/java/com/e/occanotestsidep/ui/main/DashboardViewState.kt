package com.e.occanotestsidep.ui.main

import androidx.lifecycle.LiveData
import com.e.occanotestsidep.api.responses.CylinderSearchResponse
import com.e.occanotestsidep.api.responses.DashboardListSearchResponse
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.DashMetaData
import com.e.occanotestsidep.ui.models.Status
import com.e.occanotestsidep.utils.GenericApiResponse

data class DashboardViewState(
    var cylinders: List<Cylinder>? = null,
    var statuses: List<Status>?=null,
    var metadata: DashMetaData?= null
)