package com.reactive.owm.presentation.features.home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.RestrictTo;

import com.reactive.owm.entities.City;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

import static java.util.concurrent.TimeUnit.SECONDS;

@SuppressLint("RestrictedApi")
public class HomeViewModel extends ViewModel {

    final BehaviorSubject<Boolean> progressing = BehaviorSubject.createDefault(false);
    final BehaviorSubject<String> searchInput = BehaviorSubject.create();
    final BehaviorSubject<List<City>> searchResult = BehaviorSubject.createDefault(new ArrayList<>());
    final PublishSubject<String> triggerSearch = PublishSubject.create();
    final CompositeDisposable disposables = new CompositeDisposable();
    private final Scheduler scheduler;


    public HomeViewModel() {
        this(Schedulers.computation());
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    HomeViewModel(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.disposables.add(bindTriggerSearchWithSearchInput());
    }

    private Disposable bindTriggerSearchWithSearchInput() {
        return searchInput.share()
                .observeOn(scheduler)
                .debounce(2, SECONDS, scheduler)
                .subscribe(triggerSearch::onNext);
    }


    @Override
    protected void onCleared() {
        progressing.onComplete();
        searchInput.onComplete();
        searchResult.onComplete();
        triggerSearch.onComplete();
        disposables.clear();
        super.onCleared();
    }
}
