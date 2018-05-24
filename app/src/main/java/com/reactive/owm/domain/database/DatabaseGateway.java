package com.reactive.owm.domain.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.reactive.owm.entities.City;
import com.reactive.owm.entities.FavoriteCityId;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

@Database(
        entities = {City.class, FavoriteCityId.class},
        version = 1,
        exportSchema = false)
@TypeConverters(CoordinatesConverter.class)
public abstract class DatabaseGateway extends RoomDatabase {

    public abstract CitiesTable getCitiesTable();

    public abstract FavoriteCityIdsTable getFavoriteCityIdsTable();

}



