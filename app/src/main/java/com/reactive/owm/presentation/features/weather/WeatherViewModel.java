package com.reactive.owm.presentation.features.weather;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;

import com.reactive.owm.domain.server.ServerGateway;
import com.reactive.owm.entities.ForecastsResponse;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class WeatherViewModel extends ViewModel {

    final BehaviorSubject<ForecastsResponse> forecastsResponseSubject = BehaviorSubject.create();

    @SuppressLint("CheckResult")
    void loadCityForecast(ServerGateway serverGateway, long cityId) {
        serverGateway.requestFiveDaysForecasts(cityId)
                .subscribeOn(Schedulers.io())
                .subscribe(forecastsResponseSubject::onNext);
    }

    @Override
    protected void onCleared() {
        forecastsResponseSubject.onComplete();
        super.onCleared();
    }
}
