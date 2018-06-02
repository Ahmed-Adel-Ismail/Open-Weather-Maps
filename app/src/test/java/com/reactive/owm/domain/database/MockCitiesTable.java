package com.reactive.owm.domain.database;

import android.arch.paging.DataSource;

import com.reactive.owm.entities.City;
import com.reactive.owm.entities.Coordinates;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MockCitiesTable implements CitiesTable {

    public static final int CITIES_COUNT = 5;
    public static final int CITIES_COUNT_WITH_LETTER_O = 3;
    public final List<City> cities = new ArrayList<>();

    MockCitiesTable() {
        cities.add(new City(1L, "one", "ONE", new Coordinates(1D, 1D)));
        cities.add(new City(2L, "two", "TWO", new Coordinates(2D, 2D)));
        cities.add(new City(3L, "three", "THREE", new Coordinates(3D, 3D)));
        cities.add(new City(4L, "four", "FOUR", new Coordinates(4D, 4D)));
        cities.add(new City(5L, "five", "FIVE", new Coordinates(5D, 5D)));
    }

    @Override
    public List<City> queryCityByName(String fuzzyName) {
        return Observable.fromIterable(cities)
                .filter(city -> city.name.contains(fuzzyName.trim().replace("%", "")))
                .toList()
                .blockingGet();
    }

    @Override
    public DataSource.Factory<Integer, City> queryAllCities() {
        throw new UnsupportedOperationException("not testable in the current phase");
    }

    @Override
    public Single<Integer> queryCitiesCount() {
        return Single.just(cities.size());
    }

    @Override
    public Flowable<List<City>> queryCitiesByIds(List<Long> ids) {
        return Flowable.fromIterable(cities)
                .filter(city -> ids.contains(city.id))
                .toList()
                .toFlowable();
    }
}