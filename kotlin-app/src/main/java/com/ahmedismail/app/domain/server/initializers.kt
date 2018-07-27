package com.ahmedismail.app.domain.server

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.withContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File

private const val CACHE_DIRECTORY_NAME = "responses"
private const val CACHE_DIRECTORY_SIZE_MB = (10 * 1024 * 1024).toLong()


internal suspend fun cache(context: Context) =
        withContext(CommonPool) {
            context.cacheDir
                    .let { File(it, CACHE_DIRECTORY_NAME) }
                    .let { Cache(it, CACHE_DIRECTORY_SIZE_MB) }
        }


internal fun networkChecker(context: Context): () -> Boolean = {
    context.getSystemService(Context.CONNECTIVITY_SERVICE)
            ?.let { it as ConnectivityManager }
            ?.activeNetworkInfo
            ?.isConnected
            ?: false
}


internal fun okHttp(cache: Cache, appKeyInterceptor: Interceptor, offlineInterceptor: Interceptor) =
        with(OkHttpClient.Builder()) {
            addInterceptor(appKeyInterceptor)
            addInterceptor(offlineInterceptor)
            cache(cache)
            build()
        }




