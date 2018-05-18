
package com.reactive.owm.entities;

import com.google.gson.annotations.SerializedName;

import io.reactivex.Maybe;

public class Clouds {

    @SerializedName("all")
    private final Long cloudiness;

    public Clouds(Long cloudiness) {
        this.cloudiness = cloudiness;
    }

    public Maybe<Long> getCloudiness() {
        return cloudiness != null ? Maybe.just(cloudiness) : Maybe.empty();
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "cloudiness=" + cloudiness +
                '}';
    }
}
