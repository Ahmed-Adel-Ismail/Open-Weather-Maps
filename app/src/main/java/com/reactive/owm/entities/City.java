package com.reactive.owm.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.reactivex.Maybe;

@Entity
public class City implements Serializable{

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    public final Long id;
    @SerializedName("name")
    public final String name;
    @SerializedName("country")
    public final String country;
    @SerializedName("coord")
    public final Coordinates coordinates;

    public City(@NonNull Long id, String name, String country, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.coordinates = coordinates;
    }

    public Maybe<Long> getId() {
        return Maybe.just(id);
    }

    public Maybe<String> getName() {
        return name != null ? Maybe.just(name) : Maybe.empty();
    }

    public Maybe<String> getCountry() {
        return country != null ? Maybe.just(country) : Maybe.empty();
    }

    public Maybe<Coordinates> getCoordinates() {
        return coordinates != null ? Maybe.just(coordinates) : Maybe.empty();
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        return id.equals(city.id)
                && (name != null ? name.equals(city.name) : city.name == null)
                && (country != null ? country.equals(city.country) : city.country == null)
                && (coordinates != null ? coordinates.equals(city.coordinates) : city.coordinates == null);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
        return result;
    }
}
