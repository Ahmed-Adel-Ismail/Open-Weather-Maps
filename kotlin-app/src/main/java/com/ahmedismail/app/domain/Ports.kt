package com.ahmedismail.app.domain

import com.ahmedismail.app.domain.adapters.Adapters
import com.ahmedismail.app.domain.adapters.DatabaseAdapter
import com.ahmedismail.app.domain.adapters.ServerAdapter
import kotlinx.coroutines.experimental.Deferred

interface Ports {
    val adapters: Deferred<Adapters>
    suspend fun <R> withDatabase(block: DatabaseAdapter.() -> R) = block(adapters.await().database)
    suspend fun <R> withServer(block: ServerAdapter.() -> R) = block(adapters.await().server)
}
