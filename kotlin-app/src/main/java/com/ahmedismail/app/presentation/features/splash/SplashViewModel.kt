package com.ahmedismail.app.presentation.features.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

class SplashViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val citiesCount = MutableLiveData<Int>()
    val error = MutableLiveData<Throwable>()


    init {
        error.value
        loading.value = false
        citiesCount.value = 0
    }

}


suspend fun SplashViewModel.countCities(citiesCounter: suspend () -> Single<Int>): Disposable {
    return if (loading.value!!) {
        Disposables.disposed()
    } else {
        loading.postValue(true)
        citiesCounter()
                .doFinally { loading.postValue(false) }
                .subscribe(citiesCount::postValue, error::postValue)
    }
}


