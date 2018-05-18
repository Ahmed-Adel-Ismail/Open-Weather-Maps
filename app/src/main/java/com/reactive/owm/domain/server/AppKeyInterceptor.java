package com.reactive.owm.domain.server;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AppKeyInterceptor implements Callable<Single<Interceptor>> {

    private static final String APP_ID_KEY = "appid";
    private static final String APP_ID_VALUE = "cc8bf0ef9fefd3794a362f69e9b0721d";

    @Override
    public Single<Interceptor> call() {
        return Single.defer(() -> Single.just(this::intercept));
    }

    private Response intercept(Interceptor.Chain chain) {
        return Single.just(chain)
                .map(Interceptor.Chain::request)
                .map(request -> requestWithAppKey(request, request.url()))
                .map(chain::proceed)
                .blockingGet();
    }

    private Request requestWithAppKey(Request original, HttpUrl originalHttpUrl) {
        return original.newBuilder()
                .url(httpUrl(originalHttpUrl))
                .build();
    }

    @NonNull
    private HttpUrl httpUrl(HttpUrl originalHttpUrl) {
        return originalHttpUrl.newBuilder()
                .addQueryParameter(APP_ID_KEY, APP_ID_VALUE)
                .build();
    }
}