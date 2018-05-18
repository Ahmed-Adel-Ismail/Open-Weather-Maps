package com.reactive.owm.domain.server;

import android.content.Context;

import java.io.File;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import okhttp3.Cache;

class CacheInitializer implements Function<Context, Single<Cache>> {

    private static final String CACHE_DIRECTORY_NAME = "responses";
    private static final long CACHE_DIRECTORY_SIZE_MB = 10 * 1024 * 1024;

    @Override
    public Single<Cache> apply(Context context) {
        return Single.defer(() -> cache(context));
    }

    private Single<Cache> cache(Context context) {
        return Single.just(context)
                .map(Context::getCacheDir)
                .map(directory -> new File(directory, CACHE_DIRECTORY_NAME))
                .map(file -> new Cache(file, CACHE_DIRECTORY_SIZE_MB));
    }
}
