package com.reactive.owm.presentation.features.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.reactive.owm.App;
import com.reactive.owm.R;
import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.database.CitiesTable;
import com.reactive.owm.domain.database.DatabaseGateway;
import com.reactive.owm.presentation.components.ViewModelInitializer;
import com.reactive.owm.presentation.features.home.HomeActivity;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class SplashActivity extends AppCompatActivity {

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
        disposables.add(bind());
    }

    @NonNull
    private Disposable bind() {
        return new ViewModelInitializer<>(SplashViewModel.class)
                .apply(this)
                .doOnSuccess(viewModel -> disposables.add(bindProgressBar(viewModel.progressing)))
                .doOnSuccess(viewModel -> disposables.add(bindTextView(viewModel.citiesCount)))
                .doOnSuccess(viewModel -> disposables.add(requestCitiesCount(viewModel)))
                .doOnSuccess(viewModel -> disposables.add(bindScreenNavigation(viewModel.finishScreen)))
                .subscribe();
    }

    private Disposable bindProgressBar(BehaviorSubject<Boolean> progressing) {
        ProgressBar progressBar = findViewById(R.id.splash_progress);
        return progressing.share()
                .map(loading -> loading ? View.VISIBLE : View.GONE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(progressBar::setVisibility);
    }

    private Disposable bindTextView(BehaviorSubject<Integer> progressingLabel) {
        TextView textView = findViewById(R.id.splash_label);
        return progressingLabel.share()
                .map(count -> getString(R.string.supported_cities) + count)
                .defaultIfEmpty(getString(R.string.no_cities_supported))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textView::setText);
    }

    private Disposable requestCitiesCount(SplashViewModel viewModel) {
        return new CitiesCountRequester()
                .apply(viewModel.progressing, viewModel.citiesCount)
                .subscribe(viewModel.disposables::add);
    }

    private Disposable bindScreenNavigation(BehaviorSubject<Boolean> finishScreen) {
        return finishScreen.share()
                .filter(Boolean::booleanValue)
                .observeOn(AndroidSchedulers.mainThread())
                .map(ignoredBoolean -> new Intent(this, HomeActivity.class))
                .subscribe(this::moveToNextScreen);
    }

    private void moveToNextScreen(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        disposables.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
