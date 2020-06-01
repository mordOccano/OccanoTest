package com.e.occanotestsidep.repository

import com.e.occanosidetest.utils.StaticAddress.Companion.CACHE_ERROR_TIMEOUT
import com.e.occanosidetest.utils.StaticAddress.Companion.CACHE_TIMEOUT
import com.e.occanosidetest.utils.StaticAddress.Companion.NETWORK_ERROR_TIMEOUT
import com.e.occanosidetest.utils.StaticAddress.Companion.NETWORK_TIMEOUT
import com.e.occanosidetest.utils.StaticAddress.Companion.UNKNOWN_ERROR
import com.e.occanotestsidep.utils.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

private val TAG : String = "AppDebug"

suspend fun <T>safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ApiResult<T?>{
    return withContext(dispatcher){
        try {
            withTimeout(NETWORK_TIMEOUT){
                ApiResult.Success(apiCall.invoke())
            }
        }catch (throwable:Throwable){
            throwable.printStackTrace()
            when(throwable){
                is TimeoutCancellationException ->{
                    val code = 408
                    ApiResult.GenericError(code,NETWORK_ERROR_TIMEOUT)
                }
                is IOException ->{
                    ApiResult.NetworkError
                }
                is HttpException ->{
                    val code = throwable.code()
                    val eroorResponse = convertErrorBody(throwable)
                    ApiResult.GenericError(
                        code,
                        eroorResponse
                    )
                }
                else ->{
                    ApiResult.GenericError(
                        null,
                        UNKNOWN_ERROR
                    )
                }
            }
        }
    }
}

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend() -> T?
):CacheResult<T?>{
    return withContext(dispatcher){
        try {
            withTimeout(CACHE_TIMEOUT){
                CacheResult.Success(cacheCall.invoke())
            }

        }catch (throable: Throwable){
            when(throable){
                is TimeoutCancellationException ->{
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }
                else ->{
                    CacheResult.GenericError(UNKNOWN_ERROR)
                }
            }
        }
    }
}



fun <ViewState> buildError(
    message: String,
    uiComponentType: UIComponentType,
    stateEvent: StateEvent?
): DataState<ViewState>{
    return DataState.error(
        message = "${stateEvent?.errorInfo()}\n\nReason: ${message}"
    )

}

private fun convertErrorBody(throwable: HttpException):String?{
    return try {
        throwable.response()?.errorBody()?.string()
    }catch (exception: Exception){
        UNKNOWN_ERROR
    }
}