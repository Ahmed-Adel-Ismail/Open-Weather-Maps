package com.ahmedismail.app.domain.server

import android.content.Context
import com.ahmedismail.app.entities.ForecastsResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val OPEN_WEATHER_MAPS_URL = "http://api.openweathermap.org"

interface ServerGateway {
    @GET("/data/2.5/forecast")
    fun requestFiveDaysForecasts(@Query("id") id: Long?): Single<ForecastsResponse>
}

suspend fun server(context: Context): ServerGateway =
        Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_MAPS_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp(cache(context), appKeyInterceptor(), offlineInterceptor(networkChecker(context))))
                .build()
                .create(ServerGateway::class.java)