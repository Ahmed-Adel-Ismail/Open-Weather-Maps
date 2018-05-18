package com.reactive.owm.domain.server;

import com.reactive.owm.entities.ForecastsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerGateway {

    String OPEN_WEATHER_MAPS_BASE_URL = "http://api.openweathermap.org";

    @GET("/data/2.5/forecast")
    Single<ForecastsResponse> requestFiveDaysForecasts(@Query("id") Long id);


}
