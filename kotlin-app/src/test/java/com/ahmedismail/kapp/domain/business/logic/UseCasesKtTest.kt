package com.ahmedismail.kapp.domain.business.logic

import com.ahmedismail.kapp.domain.usecases.validateCityName
import com.ahmedismail.kapp.entities.City
import org.junit.Assert.*
import org.junit.Test

class UseCasesKtTest {

    @Test
    fun validateCityNameWithValidNameThenReturnValidCityList() {
        ::query.validateCityName("a")
                .let { assertEquals(1, it.invoke()[0].id) }
    }

    @Test
    fun validateCityNameWithInvalidNameThenReturnEmptyCityList() {
        ::query.validateCityName("-1")
                .let { assertTrue(it.invoke().isEmpty()) }
    }

    @Test
    fun validateCityNameWithEmptyNameThenReturnEmptyCityList() {
        ::query.validateCityName("")
                .let { assertTrue(it.invoke().isEmpty()) }
    }


}


private fun query(name: String) =
        arrayListOf(
                City(1, "a", "", null),
                City(2, "b", "", null),
                City(3, "c", "", null))
                .filter { it.name == name.replace("%", "") }
