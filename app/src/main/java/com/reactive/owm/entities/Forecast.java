
package com.reactive.owm.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Maybe;

public class Forecast {

    @SerializedName("clouds")
    private final Clouds clouds;
    @SerializedName("dt")
    private final Long date;
    @SerializedName("dt_txt")
    private final String dateText;
    @SerializedName("main")
    private final ForecastDetails details;
    @SerializedName("snow")
    private final Snow snow;
    @SerializedName("weather")
    private final List<Weather> weather;
    @SerializedName("wind")
    private final Wind wind;

    public Forecast(Clouds clouds, Long date, String dateText, ForecastDetails details,
                    Snow snow, List<Weather> weather, Wind wind) {
        this.clouds = clouds;
        this.date = date;
        this.dateText = dateText;
        this.details = details;
        this.snow = snow;
        this.weather = weather;
        this.wind = wind;
    }

    public Maybe<Clouds> getClouds() {
        return clouds != null ? Maybe.just(clouds) : Maybe.empty();
    }

    public Maybe<Long> getDate() {
        return date != null ? Maybe.just(date) : Maybe.empty();
    }

    public Maybe<String> getDateText() {
        return dateText != null ? Maybe.just(dateText) : Maybe.empty();
    }

    public Maybe<ForecastDetails> getDetails() {
        return details != null ? Maybe.just(details) : Maybe.empty();
    }

    public Maybe<Snow> getSnow() {
        return snow != null ? Maybe.just(snow) : Maybe.empty();
    }

    public Maybe<List<Weather>> getWeather() {
        return weather != null ? Maybe.just(weather) : Maybe.empty();
    }

    public Maybe<Wind> getWind() {
        return wind != null ? Maybe.just(wind) : Maybe.empty();
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "clouds=" + clouds +
                ", date=" + date +
                ", dateText='" + dateText + '\'' +
                ", details=" + details +
                ", snow=" + snow +
                ", weather=" + weather +
                ", wind=" + wind +
                '}';
    }
}
