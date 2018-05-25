package com.reactive.owm.presentation.features.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reactive.owm.R;
import com.reactive.owm.entities.City;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public class SearchCitiesAdapter extends RecyclerView.Adapter<FavoriteCityViewHolder> {

    private final BehaviorSubject<List<City>> items;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SearchCitiesAdapter(BehaviorSubject<List<City>> items, Consumer<CompositeDisposable> attachDisposable) {

        this.items = items;
        disposables.add(bindItems());

        try {
            attachDisposable.accept(disposables);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private Disposable bindItems() {
        return this.items.share()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> notifyDataSetChanged());
    }

    @NonNull
    @Override
    public FavoriteCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteCityViewHolder(parent.getContext(), view(parent), disposables::add);
    }

    private View view(@NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_search_city_item, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteCityViewHolder holder, int position) {
        holder.currentCity.onNext(items.getValue().get(position));
    }

    @Override
    public int getItemCount() {
        return items.getValue().size();
    }
}
