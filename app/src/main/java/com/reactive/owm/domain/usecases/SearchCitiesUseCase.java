package com.reactive.owm.domain.usecases;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.components.DomainRetriever;
import com.reactive.owm.domain.components.ValidTextInputFilter;
import com.reactive.owm.domain.database.CitiesQuery;
import com.reactive.owm.domain.database.CitiesTable;
import com.reactive.owm.entities.City;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

import static android.support.annotation.RestrictTo.Scope.TESTS;

@SuppressLint("RestrictedApi")
public class SearchCitiesUseCase implements Function<String, Flowable<List<City>>> {

    private final Callable<Maybe<Domain>> domainRetriever;
    private final ObservableTransformer<String, String> validTextFilter;
    private final MaybeTransformer<Domain, CitiesTable> citiesQuery;


    public SearchCitiesUseCase(Application application) {
        this(new DomainRetriever(application), new ValidTextInputFilter(), new CitiesQuery());
    }

    @RestrictTo(TESTS)
    SearchCitiesUseCase(Callable<Maybe<Domain>> domainRetriever,
                        ObservableTransformer<String, String> validTextFilter,
                        MaybeTransformer<Domain, CitiesTable> citiesQuery) {

        this.domainRetriever = domainRetriever;
        this.validTextFilter = validTextFilter;
        this.citiesQuery = citiesQuery;
    }


    @Override
    public Flowable<List<City>> apply(@NonNull String cityName) {
        return Observable.just(cityName)
                .compose(validTextFilter)
                .firstElement()
                .flatMap(ignoredText -> domainRetriever.call())
                .compose(citiesQuery)
                .map(table -> table.queryCityByName(fuzzyCityName(cityName)))
                .doOnError(Throwable::printStackTrace)
                .onErrorReturnItem(new ArrayList<>())
                .defaultIfEmpty(new ArrayList<>())
                .toFlowable();
    }

    @NonNull
    private String fuzzyCityName(@NonNull String cityName) {
        return "%" + cityName + "%";
    }
}
