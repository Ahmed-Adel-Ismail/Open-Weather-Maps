package com.reactive.owm.presentation.features.splash;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.BehaviorSubject;

import static org.junit.Assert.*;

public class SplashViewModelTest {


    @Test
    public void updateCitiesCountThenUpdateFinishScreen() {

        TestScheduler scheduler = new TestScheduler();
        SplashViewModel viewModel = new SplashViewModel(scheduler);

        viewModel.citiesCount.onNext(10);
        scheduler.advanceTimeBy(4, TimeUnit.SECONDS);

        assertTrue(viewModel.finishScreen.getValue());


    }

    @Test
    public void onClearedThenCloseAllStreams() {
        TestScheduler scheduler = new TestScheduler();
        SplashViewModel viewModel = new SplashViewModel(scheduler);

        viewModel.finishScreen.onNext(false);
        viewModel.citiesCount.onNext(1);
        viewModel.progressing.onNext(false);

        viewModel.onCleared();
        scheduler.triggerActions();

        Observable.just(viewModel.getClass())
                .map(Class::getDeclaredFields)
                .flatMap(Observable::fromArray)
                .doOnNext(field -> field.setAccessible(true))
                .filter(field -> field.getType().isAssignableFrom(BehaviorSubject.class))
                .map(field -> field.get(viewModel))
                .cast(BehaviorSubject.class)
                .map(subject -> subject.getValue() == null)
                .defaultIfEmpty(false)
                .all(Boolean::booleanValue)
                .onErrorReturnItem(false)
                .subscribe((Consumer<Boolean>) Assert::assertTrue);
    }
}