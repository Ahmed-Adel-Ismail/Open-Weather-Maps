package com.ahmedismail.app.domain.usecases

import com.ahmedismail.app.entities.City

fun ((String) -> List<City>).validateCityName(name: String?): () -> List<City> =
        if (!name.orEmpty().trim().isEmpty()) {
            { this("%$name%") }
        } else {
            { ArrayList() }
        }





