package com.reactive.owm.domain.database;

import com.reactive.owm.domain.Domain;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class CitiesQuery implements MaybeTransformer<Domain, CitiesTable> {

    @Override
    public MaybeSource<CitiesTable> apply(Maybe<Domain> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(Domain::getDatabase)
                .map(DatabaseGateway::getCitiesTable);
    }


}
