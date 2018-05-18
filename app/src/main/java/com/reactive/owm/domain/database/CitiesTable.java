package com.reactive.owm.domain.database;


import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.reactive.owm.entities.City;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface CitiesTable {

    @Query("select * from City where City.name like :fuzzyName")
    Flowable<List<City>> queryCityByName(String fuzzyName);

    @Query("select * from City order by City.name")
    DataSource.Factory<Integer, City> queryAll();

    @Query("select count(*) from City ")
    Flowable<Integer> queryCitiesCount();

    @Query("select * from City where City.name == :id")
    Flowable<List<City>> queryCityById(Long id);

    @Insert
    void insert(City city);

}
