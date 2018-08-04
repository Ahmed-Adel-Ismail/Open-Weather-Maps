package com.ahmedismail.kapp.presentation.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedismail.kapp.domain.adapters.suspendedCitySearchQuery
import com.ahmedismail.kapp.entities.City
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class HomeScreenKtTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun searchForCityWithValidNameThenUpdateSearchResultLiveDataWithAllResults() = runBlocking {
        val result = ArrayList<City>()
        val viewModel = HomeViewModel()

        viewModel.searchInput.value = "ca"
        viewModel.searchResult.observeForever {
            result.addAll(it)
        }

        viewModel.searchForCity { suspendedCitySearchQuery(it) }

        assertEquals(2, result.size)

    }

    @Test
    fun searchForCityMultipleTimesWithValidNameThenUpdateSearchResultLiveDataWithLastResult() = runBlocking {
        val result = ArrayList<City>()
        val viewModel = HomeViewModel()

        viewModel.searchInput.value = "ca"
        viewModel.searchResult.observeForever {
            result.addAll(it)
        }

        val jobOne = launch {
            viewModel.searchForCity(::suspendedCitySearchQuery)
        }

        viewModel.searchInput.value = "dubai"

        val jobTwo = launch {
            viewModel.searchForCity(::suspendedCitySearchQuery)
        }

        jobOne.join()
        jobTwo.join()

        assertEquals(1, result.size)

    }

    @Test
    fun searchForCityWithInvalidNameThenUpdateSearchResultLiveDataWithEmptyData() = runBlocking {
        val result = ArrayList<City>()
        val viewModel = HomeViewModel()

        viewModel.searchResult.observeForever {
            result.addAll(it)
        }

        viewModel.searchForCity(::suspendedCitySearchQuery)

        assertTrue(result.isEmpty())

    }

    @Test
    fun searchForCityThenUpdateLoadingLiveData() = runBlocking {
        val result = mutableListOf(false, false)
        val viewModel = HomeViewModel()

        viewModel.searchInput.value = "ca"
        viewModel.loading.observeForever {
            if (!result[0] and !result[1]) {
                result[0] = true
            }

            if (result[0] and !result[1]) {
                result[1] = true
            }
        }

        viewModel.searchForCity { suspendedCitySearchQuery(it) }

        assertTrue(result[0] and result[1])

    }

    @Test
    fun searchForCityMultipleTimesThenUpdateLoadingLiveData() = runBlocking {
        val result = mutableListOf(false)
        val viewModel = HomeViewModel()

        viewModel.searchInput.value = "ca"
        viewModel.loading.observeForever {
            result[0] = it
        }


        val jobOne = launch {
            viewModel.searchForCity(::suspendedCitySearchQuery)
        }

        viewModel.searchInput.value = "dubai"

        val jobTwo = launch {
            viewModel.searchForCity(::suspendedCitySearchQuery)
        }

        jobOne.join()
        jobTwo.join()

        assertFalse(result[0])

    }

}



