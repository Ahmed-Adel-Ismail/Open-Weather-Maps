package com.reactive.owm.domain.database;

import android.annotation.SuppressLint;
import android.support.annotation.RestrictTo;

import com.reactive.owm.domain.Domain;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("RestrictedApi")
public class CitiesQuery implements MaybeTransformer<Domain, CitiesTable> {

    private final Scheduler scheduler;

    public CitiesQuery() {
        this(Schedulers.io());
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public CitiesQuery(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public MaybeSource<CitiesTable> apply(Maybe<Domain> upstream) {
        return upstream
                .observeOn(scheduler)
                .flatMap(Domain::getDatabase)
                .map(DatabaseGateway::getCitiesTable);
    }


}
