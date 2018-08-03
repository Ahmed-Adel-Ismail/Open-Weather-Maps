package com.ahmedismail.kapp.domain.usecases

import com.ahmedismail.kapp.entities.City

infix fun ((String) -> List<City>).withSearchableName(name: String?) = name?.takeIf(::isValidTextInput)
        ?.let { { this("%$it%") } }
        ?: { ArrayList() }

private fun isValidTextInput(name: String?) = !name.orEmpty().trim().isEmpty()





