
package com.reactive.owm.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Maybe;

public class ForecastsResponse {

    @SerializedName("city")
    private final City city;
    @SerializedName("cnt")
    private final Long count;
    @SerializedName("list")
    private final List<Forecast> forecasts;

    public ForecastsResponse(City city, Long count, List<Forecast> forecasts) {
        this.city = city;
        this.count = count;
        this.forecasts = forecasts;
    }

    public Maybe<City> getCity() {
        return city != null ? Maybe.just(city) : Maybe.empty();
    }

    public Maybe<Long> getCount() {
        return count != null ? Maybe.just(count) : Maybe.empty();
    }

    public Maybe<List<Forecast>> getForecasts() {
        return forecasts != null ? Maybe.just(forecasts) : Maybe.empty();
    }

    @Override
    public String toString() {
        return "ForecastsResponse{" +
                "city=" + city +
                ", count=" + count +
                ", forecasts=" + forecasts +
                '}';
    }
}
