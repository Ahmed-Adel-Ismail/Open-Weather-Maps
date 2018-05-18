
package com.reactive.owm.entities;

import com.google.gson.annotations.SerializedName;

import io.reactivex.Maybe;

public class ForecastDetails {

    @SerializedName("grnd_level")
    private final Double grandLevel;
    @SerializedName("humidity")
    private final Long humidity;
    @SerializedName("pressure")
    private final Double pressure;
    @SerializedName("sea_level")
    private final Double seaLevel;
    @SerializedName("temp")
    private final Double temperature;
    @SerializedName("temp_max")
    private final Double maximumTemperature;
    @SerializedName("temp_min")
    private final Double minimumTemperature;

    public ForecastDetails(Double grandLevel, Long humidity, Double pressure, Double seaLevel,
                           Double temperature, Double maximumTemperature, Double minimumTemperature) {
        this.grandLevel = grandLevel;
        this.humidity = humidity;
        this.pressure = pressure;
        this.seaLevel = seaLevel;
        this.temperature = temperature;
        this.maximumTemperature = maximumTemperature;
        this.minimumTemperature = minimumTemperature;
    }

    public Maybe<Double> getGrandLevel() {
        return grandLevel != null ? Maybe.just(grandLevel) : Maybe.empty();
    }

    public Maybe<Long> getHumidity() {
        return humidity != null ? Maybe.just(humidity) : Maybe.empty();
    }

    public Maybe<Double> getPressure() {
        return pressure != null ? Maybe.just(pressure) : Maybe.empty();
    }

    public Maybe<Double> getSeaLevel() {
        return seaLevel != null ? Maybe.just(seaLevel) : Maybe.empty();
    }

    public Maybe<Double> getTemperature() {
        return temperature != null ? Maybe.just(temperature) : Maybe.empty();
    }

    public Maybe<Double> getMaximumTemperature() {
        return maximumTemperature != null ? Maybe.just(maximumTemperature) : Maybe.empty();
    }

    public Maybe<Double> getMinimumTemperature() {
        return minimumTemperature != null ? Maybe.just(minimumTemperature) : Maybe.empty();
    }

    @Override
    public String toString() {
        return "ForecastDetails{" +
                "grandLevel=" + grandLevel +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", seaLevel=" + seaLevel +
                ", temperature=" + temperature +
                ", maximumTemperature=" + maximumTemperature +
                ", minimumTemperature=" + minimumTemperature +
                '}';
    }
}
