package com.e.occanotestsidep.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.e.occanosidetest.utils.StaticAddress.Companion.ERROR_CHECK_NETWORK_CONNECTION
import com.e.occanosidetest.utils.StaticAddress.Companion.ERROR_UNKNOWN
import com.e.occanosidetest.utils.StaticAddress.Companion.NETWORK_TIMEOUT
import com.e.occanosidetest.utils.StaticAddress.Companion.TESTING_CACHE_DELAY
import com.e.occanosidetest.utils.StaticAddress.Companion.TESTING_NETWORK_DELAY
import com.e.occanosidetest.utils.StaticAddress.Companion.UNABLE_TODO_OPERATION_WO_INTERNET
import com.e.occanosidetest.utils.StaticAddress.Companion.UNABLE_TO_RESOLVE_HOST
import com.e.occanosidetest.utils.StaticAddress.Companion.isNetworkError
import com.e.occanotestsidep.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main


abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)


        GlobalScope.launch(IO){
            delay(TESTING_NETWORK_DELAY)

            withContext(Main){
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)

                    handleNetworkCall(response)
                }
            }
        }
    }

    fun handleNetworkCall(response: GenericApiResponse<ResponseObject>){

        when(response){
            is ApiSuccessResponse ->{
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse ->{
                println("DEBUG: NetworkBoundResource: ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }
            is ApiEmptyResponse ->{
                println("DEBUG: NetworkBoundResource: Request returned NOTHING (HTTP 204)")
                onReturnError("HTTP 204. Returned NOTHING.")
            }
        }
    }

    fun onReturnError(message: String){
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}
