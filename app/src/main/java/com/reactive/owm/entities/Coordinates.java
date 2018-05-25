package com.reactive.owm.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.reactivex.Maybe;

public class Coordinates implements Serializable{

    @SerializedName("lat")
    private final Double latitude;
    @SerializedName("lon")
    private final Double longitude;

    public Coordinates(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Maybe<Double> getLatitude() {
        return latitude != null ? Maybe.just(latitude) : Maybe.empty();
    }

    public Maybe<Double> getLongitude() {
        return longitude != null ? Maybe.just(longitude) : Maybe.empty();
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}



