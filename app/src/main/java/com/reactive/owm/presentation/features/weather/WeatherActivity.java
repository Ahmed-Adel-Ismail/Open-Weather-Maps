package com.reactive.owm.presentation.features.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.reactive.owm.App;
import com.reactive.owm.R;
import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.server.ServerGateway;
import com.reactive.owm.entities.City;
import com.reactive.owm.entities.Clouds;
import com.reactive.owm.entities.ForecastDetails;
import com.reactive.owm.entities.ForecastsResponse;
import com.reactive.owm.entities.Snow;
import com.reactive.owm.entities.Weather;
import com.reactive.owm.entities.Wind;
import com.reactive.owm.presentation.components.ViewModelInitializer;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class WeatherActivity extends AppCompatActivity {

    private final CompositeDisposable disposables = new CompositeDisposable();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_search_city_item);
        disposables.add(bindToViewModel());
    }

    private Disposable bindToViewModel() {
        return ((App) getApplication()).getDomain()
                .map(Domain::getServer)
                .flatMapSingle(Maybe::toSingle)
                .zipWith(getViewModel(), this::viewModelDomainZipper)
                .subscribe(this::observerForecastsResponse);
    }

    @SuppressLint("CheckResult")
    private void observerForecastsResponse(BehaviorSubject<ForecastsResponse> forecastsObject) {
        disposables.add(forecastsObject.subscribe(this::onForecastsReady));

    }

    private void onForecastsReady(ForecastsResponse response) {
        Disposable responseDisposable = response.getForecasts()
                .flatMapObservable(Observable::fromIterable)
                .doOnNext(forecast -> forecast.getClouds().subscribe(this::updateCloud))
                .doOnNext(forecast -> forecast.getWeather().subscribe(this::updateWeather))
                .doOnNext(forecast -> forecast.getSnow().subscribe(this::updateSnow))
                .doOnNext(forecast -> forecast.getDetails().subscribe(this::updateDetails))
                .doOnNext(forecast -> forecast.getDate().subscribe(this::updateDate))
                .doOnNext(forecast -> forecast.getWind().subscribe(this::updateWind))
                .subscribeOn(Schedulers.io())
                .subscribe();
        disposables.add(responseDisposable);
    }

    private void updateWind(Wind wind) {
        Log.i("WeatherActivity...", "wind is: " + wind.toString());
    }

    private void updateDate(Long date) {
        Log.i("WeatherActivity...", "date is: " + date.toString());
    }

    private void updateDetails(ForecastDetails forecastDetails) {
        Log.i("WeatherActivity...", "forecastDetails is: " + forecastDetails.toString());
    }

    private void updateSnow(Snow snow) {
        Log.i("WeatherActivity...", "snow is: " + snow.toString());
    }

    private void updateWeather(List<Weather> weathers) {
        Log.i("WeatherActivity...", "weathers is: " + weathers.toString());
    }

    private void updateCloud(Clouds clouds) {
        Log.i("WeatherActivity...", "Clouds is: " + clouds.toString());
    }


    private BehaviorSubject<ForecastsResponse> viewModelDomainZipper(ServerGateway gateway, WeatherViewModel viewModel) {
        if (viewModel.forecastsResponseSubject.getValue() == null)
            viewModel.loadCityForecast(gateway, getCityId());
        return viewModel.forecastsResponseSubject;
    }

    private Single<WeatherViewModel> getViewModel() {
        return new ViewModelInitializer<>(WeatherViewModel.class)
                .apply(this);
    }

    private Long getCityId() {
        return ((City) getIntent().
                getSerializableExtra(getString(R.string.EXTRA_WEATHER_SCREEN_CITY)))
                .id;
    }
}
