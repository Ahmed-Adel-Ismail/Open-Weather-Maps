package com.ahmedismail.kapp.domain.adapters

import com.ahmedismail.kapp.entities.City
import kotlinx.coroutines.experimental.delay

fun simpleCitySearchQuery(name: String) =
        arrayListOf(
                City(1, "a", "", null),
                City(2, "b", "", null),
                City(3, "c", "", null))
                .filter { it.name == name.replace("%", "") }


fun suspendedCitySearchQuery(name: String): List<City> {
    Thread.sleep(300)
    return arrayListOf(
            City(1, "ankara", "", null),
            City(2, "belgium", "", null),
            City(3, "casablanca", "", null),
            City(4, "cairo", "", null),
            City(5, "new york", "", null),
            City(6, "dubai", "", null))
            .filter { it.name!!.contains(name.replace("%", ""), true) }
}