package com.ahmedismail.app.domain.usecases

import com.ahmedismail.app.domain.Domain
import com.ahmedismail.app.domain.components.isValidInput
import kotlinx.coroutines.experimental.Deferred

suspend fun Deferred<Domain>.countCities() =
        await().database
                .citiesTable
                .queryCitiesCount()


suspend fun Deferred<Domain>.findCityById(ids: List<Long>) =
        await().database
                .citiesTable
                .queryCitiesByIds(ids)

suspend fun Deferred<Domain>.findCityByName(name: String) =
        takeIf { name.isValidInput() }
                ?.await()
                ?.database
                ?.citiesTable
                ?.queryCityByName("%$name%")
                ?: ArrayList()

