package com.ahmedismail.kapp.domain.usecases

import com.ahmedismail.kapp.entities.City
import org.junit.Assert.*
import org.junit.Test

class UseCasesKtTest {

    @Test
    fun withSearchableNameWithValidNameThenReturnValidCityList() {
        ::query.withSearchableName("a")
                .let { assertEquals(1, it()[0].id) }
    }

    @Test
    fun withSearchableNameWithInvalidNameThenReturnEmptyCityList() {
        ::query.withSearchableName("-1")
                .let { assertTrue(it().isEmpty()) }
    }

    @Test
    fun withSearchableNameWithEmptyNameThenReturnEmptyCityList() {
        ::query.withSearchableName("")
                .let { assertTrue(it().isEmpty()) }
    }


}


private fun query(name: String) =
        arrayListOf(
                City(1, "a", "", null),
                City(2, "b", "", null),
                City(3, "c", "", null))
                .filter { it.name == name.replace("%", "") }
