package com.reactive.owm.domain.server;

import io.reactivex.Single;
import io.reactivex.functions.Function3;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

class OkHttpInitializer implements Function3<Cache, Interceptor, Interceptor, OkHttpClient> {

    @Override
    public OkHttpClient apply(Cache cache, Interceptor appKeyInterceptor, Interceptor offlineInterceptor) {
        return Single.just(new OkHttpClient.Builder())
                .doOnSuccess(builder -> builder.addInterceptor(appKeyInterceptor))
                .doOnSuccess(builder -> builder.addInterceptor(offlineInterceptor))
                .doOnSuccess(builder -> builder.cache(cache))
                .map(OkHttpClient.Builder::build)
                .blockingGet();
    }
}
