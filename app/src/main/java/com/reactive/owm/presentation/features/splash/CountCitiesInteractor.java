package com.reactive.owm.presentation.features.splash;

import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

class CountCitiesInteractor implements FlowableTransformer<Integer, Disposable> {


    private final BehaviorSubject<Boolean> progress;
    private final BehaviorSubject<Integer> citiesCount;

    CountCitiesInteractor(@NonNull BehaviorSubject<Boolean> progress,
                          @NonNull BehaviorSubject<Integer> citiesCount) {

        this.progress = progress;
        this.citiesCount = citiesCount;
    }

    @Override
    public Publisher<Disposable> apply(Flowable<Integer> upstream) {
        return !progress.getValue() && citiesCount.getValue() == null
                ? Flowable.just(interact(upstream))
                : Flowable.empty();
    }

    private Disposable interact(Flowable<Integer> upstream) {

        progress.onNext(true);
        return upstream
                .doFinally(() -> progress.onNext(false))
                .doOnNext(result -> System.out.println("use-case result : " + result))
                .subscribe(citiesCount::onNext, Throwable::printStackTrace);
    }
}