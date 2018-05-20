package com.reactive.owm.domain.server;

import android.content.Context;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerGatewayInitializer implements Function<Context, Single<ServerGateway>> {


    @Override
    public Single<ServerGateway> apply(Context context) {
        return Single.zip(cache(context), appKeyInterceptor(), offlineInterceptor(context), new OkHttpInitializer())
                .map(this::serverGateway);
    }

    private Single<Cache> cache(Context context) {
        return new CacheInitializer().apply(context);
    }

    private Single<Interceptor> appKeyInterceptor() {
        return new AppKeyInterceptor().call();
    }

    private Single<Interceptor> offlineInterceptor(Context context) {
        return new OfflineInterceptor(new NetworkAvailability(context)).call();
    }

    private ServerGateway serverGateway(OkHttpClient client) {
        String openWeatherMapsBaseUrl = "http://api.openweathermap.org";
        return new Retrofit.Builder()
                .baseUrl(openWeatherMapsBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ServerGateway.class);
    }
}
