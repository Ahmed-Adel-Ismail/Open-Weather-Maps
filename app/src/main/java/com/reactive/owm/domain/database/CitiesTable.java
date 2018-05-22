package com.reactive.owm.domain.database;


import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.reactive.owm.entities.City;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CitiesTable {


    @Query("select * from City where City.name like :fuzzyName")
    Flowable<List<City>> queryCityByName(String fuzzyName);

    @Query("select * from City order by City.name")
    DataSource.Factory<Integer, City> queryAllCities();

    @Query("select count(*) from City ")
    Single<Integer> queryCitiesCount();

    @Query("select * from City where City.id in (:ids)")
    Flowable<List<City>> queryCitiesByIds(List<Long> ids);

}
