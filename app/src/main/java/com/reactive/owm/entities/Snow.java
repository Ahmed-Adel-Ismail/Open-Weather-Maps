
package com.reactive.owm.entities;

import com.google.gson.annotations.SerializedName;

import io.reactivex.Maybe;

public class Snow {

    @SerializedName("3h")
    private final Double volumeForLastThreeHours;

    public Snow(Double volumeForLastThreeHours) {
        this.volumeForLastThreeHours = volumeForLastThreeHours;
    }

    public Maybe<Double> getVolumeForLastThreeHours() {
        return volumeForLastThreeHours != null ? Maybe.just(volumeForLastThreeHours) : Maybe.empty();
    }

    @Override
    public String toString() {
        return "Snow{" +
                "volumeForLastThreeHours=" + volumeForLastThreeHours +
                '}';
    }
}
