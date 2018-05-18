package com.reactive.owm.domain.server;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class NetworkAvailability implements Callable<Single<Boolean>> {

    private final WeakReference<Context> contextReference;

    public NetworkAvailability(Context context) {
        this.contextReference = new WeakReference<>(context);
    }

    @Override
    public Single<Boolean> call() {
        return Single.defer(this::isNetworkAvailable);

    }

    private Single<Boolean> isNetworkAvailable() {
        return Maybe.just(contextReference)
                .filter(reference -> reference.get() != null)
                .map(WeakReference::get)
                .map(this::toConnectivityManager)
                .map(ConnectivityManager::getActiveNetworkInfo)
                .map(NetworkInfo::isConnected)
                .toSingle(false);
    }

    private ConnectivityManager toConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}