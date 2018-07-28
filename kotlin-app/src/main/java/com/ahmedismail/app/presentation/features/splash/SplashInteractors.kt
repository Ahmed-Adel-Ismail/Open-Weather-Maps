package com.ahmedismail.app.presentation.features.splash

import com.ahmedismail.app.withPorts
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

fun SplashActivity.loadCitiesCount() {
    withPorts {
        withDatabase {
            citiesTable::queryCitiesCount
                    .let(viewModel::countCities)
                    .let(disposables::add)
        }
    }
}

fun SplashViewModel.countCities(citiesCounter: () -> Single<Int>): Disposable {
    return if (loading.value!!) {
        Disposables.disposed()
    } else {
        loading.postValue(true)
        error.postValue(null)
        citiesCounter()
                .doFinally { loading.postValue(false) }
                .subscribe(citiesCount::postValue, error::postValue)
    }
}