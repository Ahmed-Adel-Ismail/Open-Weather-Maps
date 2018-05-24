package com.reactive.owm.domain.usecases;

import android.app.Application;

import com.reactive.owm.domain.database.CitiesQuery;
import com.reactive.owm.domain.database.CitiesTable;
import com.reactive.owm.domain.components.DomainRetriever;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class CountCitiesUseCase implements Function<Application, Flowable<Integer>> {

    @Override
    public Flowable<Integer> apply(Application application) {
        return new DomainRetriever()
                .apply(application)
                .compose(new CitiesQuery())
                .flatMapSingle(CitiesTable::queryCitiesCount)
                .toFlowable();

    }
}
