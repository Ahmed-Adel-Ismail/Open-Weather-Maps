package com.reactive.owm.domain.database;

import com.reactive.owm.entities.FavoriteCityId;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class MockFavoriteCityIdsTable implements FavoriteCityIdsTable {

    public final List<FavoriteCityId> favoriteCityIds = new ArrayList<>();

    MockFavoriteCityIdsTable() {
        favoriteCityIds.add(new FavoriteCityId(1L));
        favoriteCityIds.add(new FavoriteCityId(3L));
        favoriteCityIds.add(new FavoriteCityId(5L));
    }

    @Override
    public Flowable<List<FavoriteCityId>> queryFavoriteCityIds() {
        return Flowable.just(favoriteCityIds);
    }

    @Override
    public void insertFavoriteCityId(FavoriteCityId favoriteCityId) {
        favoriteCityIds.add(favoriteCityId);
    }
}