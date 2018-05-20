package com.reactive.owm.domain.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.reactive.owm.entities.FavoriteCityId;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FavoriteCityIdsTable {


    @Query("select * from FavoriteCityId")
    Flowable<List<FavoriteCityId>> queryFavoruteCityIds();

    @Insert
    void insertFavoriteCityId(FavoriteCityId favoriteCityId);

}
