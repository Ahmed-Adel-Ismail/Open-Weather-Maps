package com.ahmedismail.kapp.domain.usecases

import com.ahmedismail.kapp.entities.City

fun ((String) -> List<City>).withSearchableName(name: String?): () -> List<City> =
        if (!name.orEmpty().trim().isEmpty()) {
            { this("%$name%") }
        } else {
            { ArrayList() }
        }





