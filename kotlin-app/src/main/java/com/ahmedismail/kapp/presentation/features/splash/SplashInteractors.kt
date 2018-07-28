package com.ahmedismail.kapp.presentation.features.splash

import com.ahmedismail.kapp.withPorts
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

fun SplashActivity.loadCitiesCount() {
    withPorts {
        withDatabase {
            citiesTable::queryCitiesCount
                    .let { viewModel.countCities(citiesCounter = it) }
                    .let { disposables.add(it) }
        }
    }
}

fun SplashViewModel.countCities(waitMillis: Long = 2000, citiesCounter: () -> Single<Int>): Disposable {
    return if (loading.value!!) {
        Disposables.disposed()
    } else {
        loading.postValue(true)
        error.postValue(null)
        Thread.sleep(waitMillis)
        citiesCounter()
                .doFinally { loading.postValue(false) }
                .subscribe(citiesCount::postValue, error::postValue)
    }
}