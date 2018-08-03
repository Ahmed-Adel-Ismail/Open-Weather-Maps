package com.ahmedismail.kapp.presentation.features.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.ahmedismail.kapp.R
import com.ahmedismail.kapp.presentation.components.DisposablesHolder
import com.ahmedismail.kapp.presentation.components.toMutableLiveData
import com.ahmedismail.kapp.presentation.components.withPorts
import com.ahmedismail.kapp.presentation.features.home.HomeActivity
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.experimental.delay

class SplashActivity : AppCompatActivity(), DisposablesHolder {

    val viewModel by lazy { ViewModelProviders.of(this).get(SplashViewModel::class.java) }
    override val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        bindViews()
        loadCitiesCount()
    }
}

class SplashViewModel(
        val loading: MutableLiveData<Boolean> = false.toMutableLiveData,
        val citiesCount: MutableLiveData<Int> = MutableLiveData(),
        val error: MutableLiveData<Throwable> = MutableLiveData(),
        val navigateToNextScreen: MutableLiveData<Boolean> = false.toMutableLiveData)
    : ViewModel()


@SuppressLint("SetTextI18n")
fun SplashActivity.bindViews() = with(viewModel) {

    citiesCount.observe(this@bindViews, Observer { splash_label.text = "supported cities: $it" })

    error.observe(this@bindViews, Observer { it?.run { splash_label.text = "error: $message" } })

    loading.observe(this@bindViews, Observer { splash_progress.visibility = if (it) VISIBLE else GONE })

    navigateToNextScreen.observe(this@bindViews, Observer {
        if (it) {
            startActivity(Intent(this@bindViews, HomeActivity::class.java))
            finish()
        }
    })
}


fun SplashActivity.loadCitiesCount() = withPorts {
    viewModel.takeUnless { it.loading.value!! }
            ?.apply { loading.postValue(true) }
            ?.also { delay(2000) }
            ?.run { withDatabase { countCities(citiesTable::queryCitiesCount) } }
            ?.let(disposables::add)
}

fun SplashViewModel.countCities(citiesCounter: () -> Single<Int>): Disposable {
    loading.postValue(true)
    error.postValue(null)
    return citiesCounter()
            .doOnSuccess(citiesCount::postValue)
            .doFinally { loading.postValue(false) }
            .map { true }
            .subscribe(navigateToNextScreen::postValue, error::postValue)

}