package com.reactive.owm.domain.server;

import com.reactive.owm.entities.ForecastsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerGateway {

    @GET("/data/2.5/forecast")
    Single<ForecastsResponse> requestFiveDaysForecasts(@Query("id") Long id);


}
