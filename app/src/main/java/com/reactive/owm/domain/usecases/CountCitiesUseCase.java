package com.reactive.owm.domain.usecases;

import android.app.Application;

import com.reactive.owm.domain.database.CitiesQuery;
import com.reactive.owm.domain.database.CitiesTable;
import com.reactive.owm.domain.components.DomainRetriever;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class CountCitiesUseCase implements Callable<Flowable<Integer>> {

    private final Application application;

    public CountCitiesUseCase(Application application) {
        this.application = application;
    }

    @Override
    public Flowable<Integer> call() {
        return new DomainRetriever()
                .apply(application)
                .compose(new CitiesQuery())
                .flatMapSingle(CitiesTable::queryCitiesCount)
                .toFlowable();

    }
}
