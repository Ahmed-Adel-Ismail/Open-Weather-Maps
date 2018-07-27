package com.ahmedismail.app.domain.server

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

private const val CACHE_DIRECTORY_NAME = "responses"
private const val CACHE_DIRECTORY_SIZE_MB = (10 * 1024 * 1024).toLong()
private const val OPEN_WEATHER_MAPS_URL = "http://api.openweathermap.org"

suspend fun server(context: Context) =
        Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_MAPS_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp(cache(context), appKeyInterceptor(), offlineInterceptor(networkChecker(context))))
                .build()
                .create(ServerGateway::class.java)


private suspend fun cache(context: Context) =
        context.cacheDir
                .let { File(it, CACHE_DIRECTORY_NAME) }
                .let { Cache(it, CACHE_DIRECTORY_SIZE_MB) }


private fun networkChecker(context: Context): suspend () -> Boolean = {
    context.getSystemService(Context.CONNECTIVITY_SERVICE)
            ?.let { it as ConnectivityManager }
            ?.activeNetworkInfo
            ?.isConnected
            ?: false
}


private fun okHttp(cache: Cache, appKeyInterceptor: Interceptor, offlineInterceptor: Interceptor) =
        with(OkHttpClient.Builder()) {
            addInterceptor(appKeyInterceptor)
            addInterceptor(offlineInterceptor)
            cache(cache)
            build()
        }




