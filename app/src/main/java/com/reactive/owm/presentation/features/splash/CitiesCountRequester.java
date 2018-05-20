package com.reactive.owm.presentation.features.splash;

import android.annotation.SuppressLint;
import android.support.annotation.RestrictTo;

import com.reactive.owm.App;
import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.database.CitiesTable;
import com.reactive.owm.domain.database.DatabaseGateway;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

@SuppressLint("RestrictedApi")
public class CitiesCountRequester implements
        BiFunction<BehaviorSubject<Boolean>, BehaviorSubject<Integer>, Maybe<Disposable>> {

    @Override
    public Maybe<Disposable> apply(BehaviorSubject<Boolean> progressing,
                                   BehaviorSubject<Integer> citiesCount) {
        return !progressing.getValue() && citiesCount.getValue() == null
                ? Maybe.just(countCities(progressing, citiesCount))
                : Maybe.empty();

    }


    private Disposable countCities(BehaviorSubject<Boolean> progressing, BehaviorSubject<Integer> citiesCount) {
        progressing.onNext(true);
        return queryCitiesCount()
                .doFinally(() -> progressing.onNext(false))
                .subscribe(citiesCount::onNext, Throwable::printStackTrace);
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    Single<Integer> queryCitiesCount() {
        return App.getInstance()
                .observeOn(Schedulers.io())
                .flatMap(App::getDomain)
                .flatMap(Domain::getDatabase)
                .map(DatabaseGateway::getCitiesTable)
                .flatMapSingle(CitiesTable::queryCitiesCount);

    }
}
