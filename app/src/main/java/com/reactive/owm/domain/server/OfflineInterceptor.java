package com.reactive.owm.domain.server;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class OfflineInterceptor implements Callable<Single<Interceptor>> {

    private final NetworkAvailability networkAvailability;

    OfflineInterceptor(NetworkAvailability networkAvailability) {
        this.networkAvailability = networkAvailability;
    }

    @Override
    public Single<Interceptor> call() {
        return Single.defer(() -> Single.just(this::intercept));
    }

    private Response intercept(@NonNull Interceptor.Chain chain) {
        return Single.zip(Single.just(chain.request()), networkAvailability.call(), this::currentRequest)
                .map(chain::proceed)
                .blockingGet();
    }

    private Request currentRequest(Request request, Boolean networkAvailable) {
        return networkAvailable
                ? readFromCacheForFifteenMinutes(request)
                : readFromCacheForFiveDays(request);
    }

    private Request readFromCacheForFifteenMinutes(Request request) {
        int maxAgeFifteenMinutes = 60 * 15;
        return request.newBuilder()
                .header("Cache-Control", "public, max-age=" + maxAgeFifteenMinutes)
                .build();
    }

    private Request readFromCacheForFiveDays(Request request) {
        int maxStaleFiveDays = 60 * 60 * 24 * 5;
        return request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStaleFiveDays)
                .build();
    }


}
