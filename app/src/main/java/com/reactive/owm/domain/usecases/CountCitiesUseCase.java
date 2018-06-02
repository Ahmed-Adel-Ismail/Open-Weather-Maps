package com.reactive.owm.domain.usecases;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.RestrictTo;

import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.components.DomainRetriever;
import com.reactive.owm.domain.database.CitiesQuery;
import com.reactive.owm.domain.database.CitiesTable;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeTransformer;

import static android.support.annotation.RestrictTo.Scope.TESTS;

@SuppressLint("RestrictedApi")
public class CountCitiesUseCase implements Callable<Flowable<Integer>> {

    private final Callable<Maybe<Domain>> domainRetriever;
    private final MaybeTransformer<Domain, CitiesTable> citiesQuery;


    public CountCitiesUseCase(Application application) {
        this(new DomainRetriever(application), new CitiesQuery());
    }

    @RestrictTo(TESTS)
    CountCitiesUseCase(Callable<Maybe<Domain>> domainRetriever,
                       MaybeTransformer<Domain, CitiesTable> citiesQuery) {
        this.domainRetriever = domainRetriever;
        this.citiesQuery = citiesQuery;
    }


    @Override
    public Flowable<Integer> call() throws Exception {
        return domainRetriever.call()
                .compose(citiesQuery)
                .flatMapSingle(CitiesTable::queryCitiesCount)
                .toFlowable();

    }
}
