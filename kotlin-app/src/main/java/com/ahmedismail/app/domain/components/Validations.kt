package com.ahmedismail.app.domain.components

fun String.isValidInput() = !orEmpty().trim().isEmpty()