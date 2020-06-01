package com.e.occanotestsidep.ui.main

import com.e.occanotestsidep.utils.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}