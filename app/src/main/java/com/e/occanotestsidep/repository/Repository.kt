package com.e.occanotestsidep.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.e.occanotestsidep.api.ApiService
import com.e.occanotestsidep.api.MyRetrofitBuilder
import com.e.occanotestsidep.api.responses.AlertSearchResponse
import com.e.occanotestsidep.api.responses.AlertsListSearchResponse
import com.e.occanotestsidep.api.responses.DashboardListSearchResponse
import com.e.occanotestsidep.api.responses.GraphListSearchResponse
import com.e.occanotestsidep.persistence.AppDBK
import com.e.occanotestsidep.persistence.StatusDao
import com.e.occanotestsidep.session.SessionManager
import com.e.occanotestsidep.ui.main.DashboardViewState
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.DashMetaData
import com.e.occanotestsidep.ui.models.Status
import com.e.occanotestsidep.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object Repository {

    private val TAG: String = "AppDebug"

    /*
    1.status - get statuses
    2.cylinders - get from db the cyls.
    3. what is the features that i have to do. i dont mitravrev but if i have more calls from api
    well, i come back to jetpack.
     */

    fun getCylinders(): LiveData<DataState<DashboardViewState>> {
        return object: NetworkBoundResource<DashboardListSearchResponse, DashboardViewState>(){


            override fun createCall(): LiveData<GenericApiResponse<DashboardListSearchResponse>> {
                return MyRetrofitBuilder.apiService.getCylinders()
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<DashboardListSearchResponse>) {
                result.value = DataState.data(
                    null,
                    DashboardViewState(
                        cylinders = response.body.cylToList()
                    )
                )
            }

        }.asLiveData()
    }

    fun getStatuses(): LiveData<DataState<DashboardViewState>> {
        return object: NetworkBoundResource<AlertsListSearchResponse, DashboardViewState>(){


            override fun createCall(): LiveData<GenericApiResponse<AlertsListSearchResponse>> {
                return MyRetrofitBuilder.apiService.getAlerts()
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<AlertsListSearchResponse>) {
                result.value = DataState.data(
                    null,
                    DashboardViewState(
                        statuses = response.body.alertToListOfStatus()
                    )

                )
//                Log.e("getStatuses: ",result.value.toString() )
            }

        }.asLiveData()
    }

    fun getArchivedStatuses(): LiveData<DataState<DashboardViewState>> {
        return object: NetworkBoundResource<AlertsListSearchResponse, DashboardViewState>(){


            override fun createCall(): LiveData<GenericApiResponse<AlertsListSearchResponse>> {
                return MyRetrofitBuilder.apiService.getArchivedAlert()
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<AlertsListSearchResponse>) {
                result.value = DataState.data(
                    null,
                    DashboardViewState(
                        statuses = response.body.alertToListOfStatus()
                    )
                )
            }

        }.asLiveData()
    }

    fun getGraphDots(): LiveData<DataState<DashboardViewState>> {
        return object: NetworkBoundResource<GraphListSearchResponse, DashboardViewState>(){


            override fun createCall(): LiveData<GenericApiResponse<GraphListSearchResponse>> {
                return MyRetrofitBuilder.apiService.getGraphDots()
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<GraphListSearchResponse>) {
                result.value = DataState.data(
                    null,
                    DashboardViewState(
                        graphDots = response.body.graphDataToList()
                    )
                )
            }

        }.asLiveData()
    }

    fun updateStatus(status: Status): LiveData<DataState<DashboardViewState>> {
        return object: NetworkBoundResource<String, DashboardViewState>(){


            override fun createCall(): LiveData<GenericApiResponse<String>> {
                return MyRetrofitBuilder.apiService.putAlertAcknowledged(status.statusId)
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<String>) {
                result.value = DataState.data(
                    null,
                    DashboardViewState(
                        updateResponse = response.body
                    )
                )
            }

        }.asLiveData()
    }


    fun getMetaData(): LiveData<DataState<DashboardViewState>> {
        return object: NetworkBoundResource<DashMetaData, DashboardViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<DashMetaData>) {
                result.value = DataState.data(
                    null,
                    DashboardViewState(
                        metadata = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<DashMetaData>> {
                return MyRetrofitBuilder.apiService.getMetaData()
            }

        }.asLiveData()
    }



}
