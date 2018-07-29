package com.ahmedismail.kapp.domain

import com.ahmedismail.kapp.domain.adapters.Adapters
import com.ahmedismail.kapp.domain.adapters.DatabaseAdapter
import com.ahmedismail.kapp.domain.adapters.ServerAdapter
import kotlinx.coroutines.experimental.Deferred

interface Ports {
    val adapters: Deferred<Adapters>
    suspend fun <R> withDatabase(block: suspend DatabaseAdapter.() -> R) = block(adapters.await().database.await())
    suspend fun <R> withServer(block: suspend ServerAdapter.() -> R) = block(adapters.await().server.await())
}
