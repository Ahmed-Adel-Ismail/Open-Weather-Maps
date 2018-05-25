package com.reactive.owm.presentation.features.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.reactive.owm.R;
import com.reactive.owm.domain.components.TextInputValidation;
import com.reactive.owm.domain.usecases.SearchCitiesUseCase;
import com.reactive.owm.entities.City;
import com.reactive.owm.presentation.components.UseCaseMultipleExecutions;
import com.reactive.owm.presentation.components.ViewModelInitializer;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public class HomeActivity extends AppCompatActivity {

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        disposables.add(bindToViewModel());
    }

    @NonNull
    private Disposable bindToViewModel() {
        return new ViewModelInitializer<>(HomeViewModel.class)
                .apply(this)
                .doOnSuccess(viewModel -> disposables.add(bindProgressBar(viewModel.progressing)))
                .doOnSuccess(viewModel -> watchSearchInputChanges(viewModel.searchInput))
                .doOnSuccess(viewModel -> disposables.add(bindTriggerSearch(viewModel)))
                .doOnSuccess(viewModel -> disposables.add(bindSearchCitiesResults(viewModel.searchedCities)))
                .subscribe();
    }

    private Disposable bindProgressBar(BehaviorSubject<Boolean> progressing) {
        ProgressBar progressBar = findViewById(R.id.home_progress_bar);
        return progressing.share()
                .observeOn(AndroidSchedulers.mainThread())
                .map(loading -> loading ? View.VISIBLE : View.INVISIBLE)
                .subscribe(progressBar::setVisibility);
    }

    private void watchSearchInputChanges(BehaviorSubject<String> searchInput) {
        EditText editText = findViewById(R.id.home_search_editText);
        editText.addTextChangedListener(searchTextChangeWatcher(searchInput));
    }

    private Disposable bindTriggerSearch(HomeViewModel viewModel) {
        return viewModel.triggerSearch
                .share()
                .compose(new TextInputValidation())
                .map(cityName -> startSearchingForCity(cityName, viewModel))
                .subscribe(disposables::add);

    }

    private Disposable bindSearchCitiesResults(BehaviorSubject<List<City>> searchedCities) {
        return Single.just((RecyclerView) findViewById(R.id.home_favorites_recycler_view))
                .doOnSuccess(view -> view.setLayoutManager(new LinearLayoutManager(this)))
                .subscribe(view -> view.setAdapter(new SearchCitiesAdapter(searchedCities, disposables::add)));
    }

    @NonNull
    private TextWatcher searchTextChangeWatcher(BehaviorSubject<String> searchText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                searchText.onNext(text.toString());
            }
        };
    }

    private Disposable startSearchingForCity(String cityName, HomeViewModel viewModel) {
        return new SearchCitiesUseCase(getApplication())
                .apply(cityName)
                .compose(new UseCaseMultipleExecutions<>(viewModel.progressing, viewModel.searchedCities))
                .subscribe(viewModel.disposables::add);
    }

    @Override
    protected void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
}
