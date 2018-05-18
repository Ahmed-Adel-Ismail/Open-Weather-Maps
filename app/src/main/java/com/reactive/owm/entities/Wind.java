
package com.reactive.owm.entities;

import com.google.gson.annotations.SerializedName;

import io.reactivex.Maybe;


public class Wind {

    @SerializedName("deg")
    private final Double degree;
    @SerializedName("speed")
    private final Double speed;

    public Wind(Double degree, Double speed) {
        this.degree = degree;
        this.speed = speed;
    }

    public Maybe<Double> getDegree() {
        return degree != null ? Maybe.just(degree) : Maybe.empty();
    }

    public Maybe<Double> getSpeed() {
        return speed != null ? Maybe.just(speed) : Maybe.empty();
    }

    @Override
    public String toString() {
        return "Wind{" +
                "degree=" + degree +
                ", speed=" + speed +
                '}';
    }
}
