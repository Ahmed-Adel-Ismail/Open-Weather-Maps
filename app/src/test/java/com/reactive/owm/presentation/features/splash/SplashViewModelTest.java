package com.reactive.owm.presentation.features.splash;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

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

}