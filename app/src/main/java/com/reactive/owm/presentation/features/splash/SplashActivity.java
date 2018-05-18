package com.reactive.owm.presentation.features.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.reactive.owm.R;
import com.reactive.owm.presentation.components.ViewModelInitializer;

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
        disposables.add(bindViews());
    }

    @NonNull
    private Disposable bindViews() {
        return new ViewModelInitializer<>(SplashViewModel.class)
                .apply(this)
                .doOnSuccess(viewModel -> disposables.add(bindProgressBar(viewModel.progressing)))
                .doOnSuccess(viewModel -> disposables.add(bindTextView(viewModel.progressingLabel)))
                .subscribe();
    }

    private Disposable bindProgressBar(BehaviorSubject<Boolean> progressing) {
        return progressing.share()
                .subscribe();
    }

    private Disposable bindTextView(BehaviorSubject<Integer> progressingLabel) {
        TextView textView = findViewById(R.id.splash_label);
        return progressingLabel.share()
                .observeOn(AndroidSchedulers.mainThread())
                .map(String::valueOf)
                .defaultIfEmpty("")
                .subscribe(textView::setText);
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
