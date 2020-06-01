package com.e.occanotestsidep.api

import androidx.lifecycle.LiveData
import com.e.occanotestsidep.api.responses.AlertsListSearchResponse
import com.e.occanotestsidep.api.responses.DashboardListSearchResponse
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.DashMetaData
import com.e.occanotestsidep.utils.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/dashboard")
    fun getCylinders(): LiveData<GenericApiResponse<DashboardListSearchResponse>>

    @GET("/alerts")
    fun getAlerts(): LiveData<GenericApiResponse<AlertsListSearchResponse>>
//

//    @GET("/dashboard")
//    fun getCylinders(): LiveData<GenericApiResponse<DashboardListSearchResponse>>
//            LiveData<GenericApiResponse<List<Cylinder>>>

    @GET("/report/")
    fun getMetaData(): LiveData<GenericApiResponse<DashMetaData>>

    /**all queries are here:
     * auth view model
     * 1.1. splash screen
     * 1.2. user login
     * 1.3. ship login
     * main view model
     * 2.1. dashboard - all - cylinders + Alerts
     * 2.2. calibration
     *
     * start with dashboard and after that the rest
     */

//    @GET("placeholder/user/{userId}")
//    fun getUser(
//        @Path("userId") userId: String
//    ): LiveData<GenericApiResponse<User>>
}