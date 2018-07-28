package com.ahmedismail.kapp.presentation.features.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class SplashInteractorsKtTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun countCitiesWithSuccessfulCounterThenUpdateCitiesCountLiveData() {

        val result = mutableListOf(0)
        val viewModel = SplashViewModel()

        viewModel.citiesCount.observeForever {
            result[0] = it
        }

        viewModel.countCities(0) { Single.just(10) }

        assertEquals(10, result[0])

    }

    @Test
    fun countCitiesWithSuccessfulCounterThenUpdateLoadingLiveData() {

        val startAndStop = mutableListOf(false, false)
        val viewModel = SplashViewModel()

        viewModel.loading.observeForever {
            if (!startAndStop[0] && !startAndStop[1]) {
                startAndStop[0] = true
            }

            if (startAndStop[0] && !startAndStop[1]) {
                startAndStop[1] = true
            }
        }

        viewModel.countCities(0) { Single.just(10) }

        assertTrue(startAndStop[0] && startAndStop[1])

    }

    @Test
    fun countCitiesWithFailingCounterThenUpdateLoadingLiveData() {

        val startAndStop = mutableListOf(false, false)
        val viewModel = SplashViewModel()

        viewModel.loading.observeForever {
            if (!startAndStop[0] && !startAndStop[1]) {
                startAndStop[0] = true
            }

            if (startAndStop[0] && !startAndStop[1]) {
                startAndStop[1] = true
            }
        }

        viewModel.countCities(0) { Single.error(UnsupportedOperationException("error")) }

        assertTrue(startAndStop[0] && startAndStop[1])

    }

    @Test
    fun countCitiesWithFailingCounterThenUpdateErrorLiveData() {

        val result = mutableListOf("")
        val viewModel = SplashViewModel()

        viewModel.error.observeForever {
            result[0] = it?.message ?: ""
        }

        viewModel.countCities(0) { Single.error(UnsupportedOperationException("error")) }

        assertEquals("error", result[0])

    }


}