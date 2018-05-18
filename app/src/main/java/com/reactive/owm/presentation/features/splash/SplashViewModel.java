package com.reactive.owm.presentation.features.splash;

import android.arch.lifecycle.ViewModel;

import io.reactivex.subjects.BehaviorSubject;

public class SplashViewModel extends ViewModel {

    final BehaviorSubject<Boolean> progressing = BehaviorSubject.createDefault(false);
    final BehaviorSubject<Integer> progressingLabel = BehaviorSubject.create();


    @Override
    protected void onCleared() {
        progressing.onComplete();
        progressingLabel.onComplete();
        super.onCleared();
    }
}
