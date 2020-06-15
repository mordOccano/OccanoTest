package com.e.occanotestsidep.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.e.occanotestsidep.repository.Repository
import com.e.occanotestsidep.ui.main.DashboardStateEvent.*
import com.e.occanotestsidep.ui.models.*
import com.e.occanotestsidep.utils.AbsentLiveData
import com.e.occanotestsidep.utils.DataState

class MainViewModel :ViewModel(){

    private val _stateEvent : MutableLiveData<DashboardStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<DashboardViewState> = MutableLiveData()

    val viewState:LiveData<DashboardViewState>
    get() = _viewState

    val dataState: LiveData<DataState<DashboardViewState>> = Transformations
        .switchMap(_stateEvent) {
               it?.let {
                    handleStateEvent(it)
            }
        }

    private fun handleStateEvent(stateEvent: DashboardStateEvent) : LiveData<DataState<DashboardViewState>>{

        when(stateEvent){

            is GetReport ->{
                return Repository.getReport()
            }
            is GetMainDashboard ->{
                return Repository.getCylinders()
            }


            is GetCylinders -> {
                return Repository.getCylinders()
            }

            is GetStatuses -> {
                return Repository.getStatuses()
            }

            is GetArchiveStatuses -> {
                return Repository.getCylinders()
            }
            is GetGraphDots -> {
                return Repository.getGraphDots()
            }

//            is DashboardStateEvent.UpdateStatus -> {
//                return Repository.updateStatus()
//            }

//            is DashboardStateEvent.GetMetaData ->{
//                return Repository.getMetaData()
//            }

            is None -> {
                return AbsentLiveData.create()
            }

        }
    }

    fun setCylinderData(cylinders: List<Cylinder>){
        val update = getCurrentViewStateOrNew()
        update.cylinders = cylinders
        _viewState.value = update
    }

    fun setMainData(main: MainDataDashboard){
        val update = getCurrentViewStateOrNew()
        update.cylinders = main.cylinders
        update.statuses = main.statuses
        update.archiveStatuses = main.archiveStatuses
        update.graphDots = main.graphDots
        _viewState.value = update
    }

    fun setGraphData(graphDots: List<GraphDots>){
        val update = getCurrentViewStateOrNew()
        update.graphDots = graphDots
        _viewState.value = update
    }

    fun setStatusesData(statuses: List<Alert>){
        val update = getCurrentViewStateOrNew()
        update.statuses = statuses
        _viewState.value = update
    }

    fun setArchivedStatusesData(statuses: List<Alert>){
        val update = getCurrentViewStateOrNew()
        update.statuses = statuses
        _viewState.value = update
    }

//    fun setMetHadata(metaData: DashMetaData){
//        val update = getCurrentViewStateOrNew()
//        update.metadata = metaData
//        _viewState.value = update
//    }

    private fun getCurrentViewStateOrNew(): DashboardViewState {
        return viewState.value?.let {
            it
        }?: DashboardViewState()
    }

    fun setStateEvent(event: DashboardStateEvent){
        _stateEvent.value = event
    }

}