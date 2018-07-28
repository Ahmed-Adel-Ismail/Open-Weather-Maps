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
        error.value = null
        loading.value = false
        citiesCount.value = 0
    }

}





