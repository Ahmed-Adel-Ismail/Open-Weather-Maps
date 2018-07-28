package com.ahmedismail.app.domain.adapters

import android.content.Context
import android.net.ConnectivityManager
import com.ahmedismail.app.entities.ForecastsResponse
import io.reactivex.Single
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.withContext
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File

private const val APP_ID_KEY = "appid"
private const val APP_ID_VALUE = "cc8bf0ef9fefd3794a362f69e9b0721d"
private const val MAX_AGE_FIFTEEN_MINUTES = 60 * 15
private const val MAX_AGE_FIVE_DAYS = 60 * 60 * 24 * 5
private const val CACHE_DIRECTORY_NAME = "responses"
private const val CACHE_DIRECTORY_SIZE_MB = (10 * 1024 * 1024).toLong()
private const val OPEN_WEATHER_MAPS_URL = "http://api.openweathermap.org"

interface ServerAdapter {
    @GET("/data/2.5/forecast")
    fun requestFiveDaysForecasts(@Query("id") id: Long?): Single<ForecastsResponse>
}

suspend fun serverAdapter(context: Context): ServerAdapter =
        Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_MAPS_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp(cache(context), appKeyInterceptor(), offlineInterceptor(networkChecker(context))))
                .build()
                .create(ServerAdapter::class.java)


private suspend fun cache(context: Context) =
        withContext(CommonPool) {
            context.cacheDir
                    .let { File(it, CACHE_DIRECTORY_NAME) }
                    .let { Cache(it, CACHE_DIRECTORY_SIZE_MB) }
        }


private fun networkChecker(context: Context): suspend () -> Boolean = {
    withContext(CommonPool) {
        context.getSystemService(Context.CONNECTIVITY_SERVICE)
                ?.let { it as ConnectivityManager }
                ?.activeNetworkInfo
                ?.isConnected
                ?: false
    }
}


private fun okHttp(cache: Cache, appKeyInterceptor: Interceptor, offlineInterceptor: Interceptor) =
        with(OkHttpClient.Builder()) {
            addInterceptor(appKeyInterceptor)
            addInterceptor(offlineInterceptor)
            cache(cache)
            build()
        }


private fun appKeyInterceptor() = object : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
                .let { requestWithAppKey(it, it.url()) }
                .let(chain::proceed)
    }

    private fun requestWithAppKey(original: Request, originalHttpUrl: HttpUrl): Request {
        return original.newBuilder()
                .url(httpUrl(originalHttpUrl))
                .build()
    }

    private fun httpUrl(originalHttpUrl: HttpUrl): HttpUrl {
        return originalHttpUrl.newBuilder()
                .addQueryParameter(APP_ID_KEY, APP_ID_VALUE)
                .build()
    }
}


private fun offlineInterceptor(networkChecker: suspend () -> Boolean) = object : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            chain.request()
                    .let { requestFromCacheIfAvailable(it, networkChecker()) }
                    .let(chain::proceed)
        }
    }

    private fun requestFromCacheIfAvailable(request: Request, isNetworkAvailable: Boolean): Request {
        return if (isNetworkAvailable)
            readFromCacheForFifteenMinutes(request)
        else
            readFromCacheForFiveDays(request)
    }

    private fun readFromCacheForFifteenMinutes(request: Request): Request {
        return request.newBuilder()
                .header("Cache-Control", "public, max-age=$MAX_AGE_FIFTEEN_MINUTES")
                .build()
    }

    private fun readFromCacheForFiveDays(request: Request): Request {
        return request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$MAX_AGE_FIVE_DAYS")
                .build()
    }
}