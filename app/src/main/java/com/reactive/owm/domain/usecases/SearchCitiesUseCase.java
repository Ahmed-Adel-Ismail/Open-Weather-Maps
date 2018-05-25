package com.reactive.owm.domain.usecases;

import android.app.Application;

import com.reactive.owm.domain.components.DomainRetriever;
import com.reactive.owm.domain.database.CitiesQuery;
import com.reactive.owm.entities.City;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class SearchCitiesUseCase implements Function<String, Flowable<List<City>>> {

    private final Application application;

    public SearchCitiesUseCase(Application application) {
        this.application = application;
    }

    @Override
    public Flowable<List<City>> apply(String cityName) {
        return new DomainRetriever()
                .apply(application)
                .compose(new CitiesQuery())
                .map(table -> table.queryCityByName("%" + cityName + "%"))
                .doOnError(Throwable::printStackTrace)
                .onErrorReturnItem(new ArrayList<>())
                .toFlowable();
    }
}
