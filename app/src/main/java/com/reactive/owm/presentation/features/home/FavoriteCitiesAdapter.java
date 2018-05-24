package com.reactive.owm.presentation.features.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.reactive.owm.entities.City;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public class FavoriteCitiesAdapter extends RecyclerView.Adapter<FavoriteCityViewHolder> {

    private final BehaviorSubject<List<City>> items;

    public FavoriteCitiesAdapter(BehaviorSubject<List<City>> items, Consumer<Disposable> attachDisposable) {

        this.items = items;

        Disposable disposable = this.items.share()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> notifyDataSetChanged());

        try {
            attachDisposable.accept(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public FavoriteCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteCityViewHolder holder, int position) {
        holder.invalidate(items.getValue().get(position));
    }

    @Override
    public int getItemCount() {
        return items.getValue().size();
    }
}
