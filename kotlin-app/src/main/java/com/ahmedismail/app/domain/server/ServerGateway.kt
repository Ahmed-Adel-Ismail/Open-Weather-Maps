package com.ahmedismail.app.domain.server

import com.ahmedismail.app.entities.ForecastsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerGateway {
    @GET("/data/2.5/forecast")
    fun requestFiveDaysForecasts(@Query("id") id: Long?): Single<ForecastsResponse>
}