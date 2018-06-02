package com.reactive.owm.presentation.features.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.reactive.owm.R;
import com.reactive.owm.entities.City;
import com.reactive.owm.presentation.components.Clearable;
import com.reactive.owm.presentation.features.weather.WeatherActivity;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class StartWeatherScreenReceiver extends BroadcastReceiver implements Clearable {

    private final PublishSubject<City> city = PublishSubject.create();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public StartWeatherScreenReceiver(Activity activity) {
        disposables.add(bindCity(new WeakReference<>(activity)));
    }

    private Disposable bindCity(WeakReference<Activity> activityReference) {
        return city.share()
                .filter(ignoredCity -> activityReference.get() != null)
                .map(city -> new Pair<>(activityReference.get(), city))
                .subscribe(this::navigateToWeatherScreen);
    }

    private void navigateToWeatherScreen(Pair<Activity, City> pair) {
        pair.first.startActivity(intent(pair.first, pair.second));
    }

    @NonNull
    private Intent intent(Activity activity, City city) {
        return new Intent(activity, WeatherActivity.class)
                .putExtra(activity.getString(R.string.EXTRA_WEATHER_SCREEN_CITY), city);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        city.onNext((City) intent.getSerializableExtra(context.getString(R.string.EXTRA_WEATHER_SCREEN_CITY)));
    }


    @Override
    public void onCleared() {
        disposables.dispose();
        city.onComplete();
    }
}
