package com.reactive.owm.domain.repositories;

import android.annotation.SuppressLint;
import android.support.annotation.RestrictTo;

import com.reactive.owm.App;
import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.database.CitiesTable;
import com.reactive.owm.domain.database.DatabaseGateway;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class InitializerRepository extends Repository {


    @SuppressLint("RestrictedApi")
    public InitializerRepository() {
        super(App.getInstance().flatMap(App::getDomain), Schedulers.io());
    }

    public Flowable<Integer> requestCitiesCount() {
        return domain.flatMap(Domain::getDatabase)
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .map(DatabaseGateway::getCitiesTable)
                .toFlowable()
                .flatMap(CitiesTable::queryCitiesCount);
    }


}
