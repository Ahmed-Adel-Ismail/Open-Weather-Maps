package com.reactive.owm.domain.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.reactive.owm.entities.Coordinates;

public class CoordinatesConverter {

    @TypeConverter
    public String fromCoordinates(Coordinates coordinates) {
        return new Gson().toJson(coordinates);
    }

    @TypeConverter
    public Coordinates fromString(String value) {
        return new Gson().fromJson(value, Coordinates.class);
    }


}
