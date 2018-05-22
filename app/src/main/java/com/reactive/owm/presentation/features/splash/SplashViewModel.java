package com.reactive.owm.presentation.features.splash;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

@SuppressLint("RestrictedApi")
public class SplashViewModel extends ViewModel {

    final BehaviorSubject<Boolean> progressing = BehaviorSubject.createDefault(false);
    final BehaviorSubject<Integer> citiesCount = BehaviorSubject.create();
    final BehaviorSubject<Boolean> finishScreen = BehaviorSubject.createDefault(false);
    final CompositeDisposable disposables = new CompositeDisposable();

    private final Scheduler scheduler;


    public SplashViewModel() {
        this(Schedulers.computation());
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    SplashViewModel(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.disposables.add(bindFinishScreenToCitiesCount());

    }

    @NonNull
    private Disposable bindFinishScreenToCitiesCount() {
        return citiesCount.share()
                .subscribeOn(scheduler)
                .delay(2, TimeUnit.SECONDS, scheduler)
                .map(ignoredCount -> true)
                .observeOn(scheduler)
                .subscribe(finishScreen::onNext);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        progressing.onComplete();
        citiesCount.onComplete();
        super.onCleared();
    }
}
