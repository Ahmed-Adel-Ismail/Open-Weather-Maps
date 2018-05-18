package com.reactive.owm.presentation.features.splash;

import android.arch.lifecycle.ViewModel;

import com.reactive.owm.domain.repositories.InitializerRepository;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public class SplashViewModel extends ViewModel {

    final BehaviorSubject<Boolean> progressing = BehaviorSubject.createDefault(false);
    final BehaviorSubject<Integer> progressingLabel = BehaviorSubject.create();

    private final InitializerRepository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SplashViewModel() {
        this(new InitializerRepository());
    }

    public SplashViewModel(InitializerRepository repository) {
        this.repository = repository;
        disposables.add(bindProgressLabel());
    }

    private Disposable bindProgressLabel() {
        return repository.requestCitiesCount()
                .subscribe(progressingLabel::onNext);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        progressing.onComplete();
        progressingLabel.onComplete();
        super.onCleared();
    }
}
