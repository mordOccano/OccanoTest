package com.e.occanotestsidep.api

import com.e.occanotestsidep.MainActivity
import com.e.occanotestsidep.utils.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofitBuilder {

//    var BASE_URL: String = "http://office.occano.io:4000"
    var BASE_URL: String = "http://${MainActivity().ip}:4000"

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }


    val apiService: ApiService by lazy{
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }
}