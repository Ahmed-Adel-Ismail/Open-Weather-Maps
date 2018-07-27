package com.ahmedismail.app.domain.server

import kotlinx.coroutines.experimental.runBlocking
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val APP_ID_KEY = "appid"
private const val APP_ID_VALUE = "cc8bf0ef9fefd3794a362f69e9b0721d"
private const val MAX_AGE_FIFTEEN_MINUTES = 60 * 15
private const val MAX_AGE_FIVE_DAYS = 60 * 60 * 24 * 5

internal fun appKeyInterceptor() =
        object : Interceptor {

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


internal fun offlineInterceptor(networkChecker: suspend () -> Boolean) =
        object : Interceptor {

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