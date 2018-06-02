package com.reactive.owm.presentation.features.home;

import android.support.annotation.NonNull;

import com.reactive.owm.entities.City;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

class SearchCitiesInteractor implements FlowableTransformer<List<City>, Disposable> {

    private final BehaviorSubject<Boolean> progressing;
    private final BehaviorSubject<List<City>> searchResult;

    SearchCitiesInteractor(BehaviorSubject<Boolean> progressing,
                           BehaviorSubject<List<City>> searchResult) {

        this.progressing = progressing;
        this.searchResult = searchResult;
    }

    @Override
    public Publisher<Disposable> apply(Flowable<List<City>> upstream) {
        progressing.onNext(true);
        return Flowable.just(interact(upstream));


    }

    @NonNull
    private Disposable interact(Flowable<List<City>> upstream) {
        return upstream
                .doFinally(() -> progressing.onNext(false))
                .doOnNext(result -> System.out.println("use-case result : " + result))
                .subscribe(searchResult::onNext);
    }
}
