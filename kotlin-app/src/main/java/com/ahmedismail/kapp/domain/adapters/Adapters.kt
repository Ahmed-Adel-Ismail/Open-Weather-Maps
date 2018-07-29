package com.ahmedismail.kapp.domain.adapters

import kotlinx.coroutines.experimental.Deferred

data class Adapters(val server: Deferred<ServerAdapter>,
                    val database: Deferred<DatabaseAdapter>)