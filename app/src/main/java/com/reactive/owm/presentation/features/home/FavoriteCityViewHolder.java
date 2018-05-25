package com.reactive.owm.presentation.features.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.reactive.owm.R;
import com.reactive.owm.entities.City;

import java.io.Serializable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

class FavoriteCityViewHolder extends RecyclerView.ViewHolder {

    final BehaviorSubject<City> currentCity = BehaviorSubject.create();

    FavoriteCityViewHolder(Context context,
                           View itemView,
                           Consumer<CompositeDisposable> attachDisposables) {
        super(itemView);

        CompositeDisposable disposables = new CompositeDisposable();
        disposables.add(bindRemoveButton(context, itemView));
        disposables.add(bindCityName(context, itemView.findViewById(R.id.search_item_city_name_textView)));

        try {
            attachDisposables.accept(disposables);
        } catch (Throwable e) {
            Log.e("FavoriteCityViewHolder", "failed to attach disposables", e);
        }
    }

    @NonNull
    private Disposable bindRemoveButton(Context context, View itemView) {
        return Single.just(itemView.findViewById(R.id.search_item_city_remove_Button))
                .cast(Button.class)
                .subscribe(button -> button.setOnClickListener(view -> removeFavoriteCity(context)));
    }


    private Disposable bindCityName(Context context, TextView cityNameTextView) {
        cityNameTextView.setOnClickListener(view -> startWeatherScreen(context));
        return currentCity.share()
                .doOnDispose(currentCity::onComplete)
                .subscribe(city -> updateCityNameTextView(city, cityNameTextView));
    }

    private void removeFavoriteCity(Context context) {
        if (currentCity.getValue() != null) {
            context.sendBroadcast(removeCurrentFavoriteCity(context, currentCity.getValue()));
        }
    }

    private void startWeatherScreen(Context context) {
        if (currentCity.getValue() != null) {
            context.sendBroadcast(startWeatherScreenIntent(context, currentCity.getValue()));
        }
    }

    private Disposable updateCityNameTextView(City city, TextView cityNameTextView) {
        return city.getName()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cityNameTextView::setText);
    }

    private Intent removeCurrentFavoriteCity(Context application, Serializable city) {
        return new Intent(application.getString(R.string.ACTION_REMOVE_FAVORITE_CITY))
                .putExtra(application.getString(R.string.EXTRA_REMOVE_FAVORITE_CITY), city);
    }

    private Intent startWeatherScreenIntent(Context application, City city) {
        return new Intent(application.getString(R.string.ACTION_START_WEATHER_SCREEN))
                .putExtra(application.getString(R.string.EXTRA_WEATHER_SCREEN_CITY), city);
    }

}
