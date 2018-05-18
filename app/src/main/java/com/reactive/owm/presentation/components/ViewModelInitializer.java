package com.reactive.owm.presentation.components;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class ViewModelInitializer<T extends ViewModel> implements Function<FragmentActivity, Single<T>> {

    private final Class<T> viewModelClass;

    public ViewModelInitializer(Class<T> viewModelClass) {
        this.viewModelClass = viewModelClass;
    }

    @Override
    public Single<T> apply(FragmentActivity fragmentActivity) {
        return Single.just(ViewModelProviders.of(fragmentActivity).get(viewModelClass));
    }
}
