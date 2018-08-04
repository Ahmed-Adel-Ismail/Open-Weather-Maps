package com.ahmedismail.kapp.domain.usecases

import com.ahmedismail.kapp.domain.adapters.simpleCitySearchQuery
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UseCasesKtTest {

    @Test
    fun withSearchableNameWithValidNameThenReturnValidCityList() {
        ::simpleCitySearchQuery.withSearchableName("a")
                .run { assertEquals(1, this()[0].id) }
    }

    @Test
    fun withSearchableNameWithInvalidNameThenReturnEmptyCityList() {
        ::simpleCitySearchQuery.withSearchableName("-1")
                .run { assertTrue(this().isEmpty()) }
    }

    @Test
    fun withSearchableNameWithEmptyNameThenReturnEmptyCityList() {
        ::simpleCitySearchQuery.withSearchableName("")
                .run { assertTrue(this().isEmpty()) }
    }
}



