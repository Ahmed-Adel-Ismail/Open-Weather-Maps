package com.ahmedismail.app.presentation.features.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedismail.app.domain.Domain
import com.ahmedismail.app.domain.usecases.citiesCount
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.coroutines.experimental.Deferred

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


suspend fun SplashViewModel.countCities(domain: Deferred<Domain>): Disposable {
    return if (loading.value!!) {
        Disposables.disposed()
    } else {
        loading.postValue(true)
        domain.citiesCount()
                .doFinally { loading.postValue(false) }
                .subscribe(citiesCount::postValue, error::postValue)
    }
}


