package com.e.occanotestsidep.ui.main

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bumptech.glide.RequestManager
import com.e.occanotestsidep.persistence.AppDBK
import com.e.occanotestsidep.persistence.StatusDao
import com.e.occanotestsidep.repository.Repository
import com.e.occanotestsidep.session.SessionManager
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.DashMetaData
import com.e.occanotestsidep.ui.models.Status
import com.e.occanotestsidep.utils.AbsentLiveData
import com.e.occanotestsidep.utils.DataState

class MainViewModel

constructor(
//    private val sessionManager: SessionManager,
//    private val repository: Repository
//    ,
//    private val sharedPreferences: SharedPreferences,
//    private val requestManager: RequestManager
)  :ViewModel(){

    private val _stateEvent : MutableLiveData<DashboardStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<DashboardViewState> = MutableLiveData()

    val viewState:LiveData<DashboardViewState>
    get() = _viewState

    val dataState: LiveData<DataState<DashboardViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
                stateEvent?.let {
                    handleStateEvent(it)
            }
        }

    private fun handleStateEvent(stateEvent: DashboardStateEvent) : LiveData<DataState<DashboardViewState>>{

        when(stateEvent){

            is DashboardStateEvent.GetMainDashboard ->{
                return Repository.getCylinders()
            }

            is DashboardStateEvent.GetCylinders -> {
                return Repository.getCylinders()
            }

            is DashboardStateEvent.GetStatuses -> {
                return Repository.getStatuses()
            }

            is DashboardStateEvent.GetArchiveStatuses -> {
                return Repository.getArchivedStatuses()
            }

            is DashboardStateEvent.GetMetaData ->{
                return Repository.getMetaData()
            }

            is DashboardStateEvent.None -> {
                return AbsentLiveData.create()
            }

        }
    }

    fun setCylinderData(cylinders: List<Cylinder>){
        val update = getCurrentViewStateOrNew()
        update.cylinders = cylinders
        _viewState.value = update
    }

    fun setStatusesData(statuses: List<Status>){
        val update = getCurrentViewStateOrNew()
        update.statuses = statuses
        _viewState.value = update
    }

    fun setArchivedStatusesData(statuses: List<Status>){
        val update = getCurrentViewStateOrNew()
        update.statuses = statuses
        _viewState.value = update
    }

    fun setMetHadata(metaData: DashMetaData){
        val update = getCurrentViewStateOrNew()
        update.metadata = metaData
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): DashboardViewState {
        val  value = viewState.value?.let {
            it
        }?: DashboardViewState(

        )
        return value
    }

    fun setStateEvent(event: DashboardStateEvent){
        _stateEvent.value = event
    }

}