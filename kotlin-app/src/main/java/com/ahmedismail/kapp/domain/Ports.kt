package com.ahmedismail.kapp.domain

import com.ahmedismail.kapp.domain.adapters.Adapters
import com.ahmedismail.kapp.domain.adapters.DatabaseAdapter
import com.ahmedismail.kapp.domain.adapters.ServerAdapter
import kotlinx.coroutines.experimental.Deferred

interface Ports {
    val adapters: Deferred<Adapters>
    suspend fun <R> withDatabase(block: DatabaseAdapter.() -> R) = block(adapters.await().database)
    suspend fun <R> withServer(block: ServerAdapter.() -> R) = block(adapters.await().server)
}
