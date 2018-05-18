
package com.reactive.owm.entities;

import com.google.gson.annotations.SerializedName;

import io.reactivex.Maybe;

public class Weather {

    @SerializedName("description")
    private final String description;
    @SerializedName("icon")
    private final String icon;
    @SerializedName("id")
    private final Long id;
    @SerializedName("main")
    private String state;

    public Weather(String description, String icon, Long id, String state) {
        this.description = description;
        this.icon = icon;
        this.id = id;
        this.state = state;
    }

    public Maybe<String> getDescription() {
        return description != null ? Maybe.just(description) : Maybe.empty();
    }

    public Maybe<String> getIcon() {
        return icon != null ? Maybe.just(icon) : Maybe.empty();
    }

    public Maybe<Long> getId() {
        return id != null ? Maybe.just(id) : Maybe.empty();
    }

    public Maybe<String> getState() {
        return state != null ? Maybe.just(state) : Maybe.empty();
    }

    @Override
    public String toString() {
        return "Weather{" +
                "description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", id=" + id +
                ", state='" + state + '\'' +
                '}';
    }
}
