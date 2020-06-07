package com.e.occanotestsidep.ui.main

sealed class DashboardStateEvent {

    class GetCylinders: DashboardStateEvent()

    class GetStatuses: DashboardStateEvent()

    class GetArchiveStatuses: DashboardStateEvent()

    class GetGraphDots: DashboardStateEvent()

//    class UpdateStatus: DashboardStateEvent()

    class GetMainDashboard: DashboardStateEvent()

    class GetMetaData: DashboardStateEvent()


    class None: DashboardStateEvent()
}