package com.ahmedismail.app.presentation.features.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ahmedismail.app.R
import com.ahmedismail.app.getDomain
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.experimental.launch

class SplashActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(SplashViewModel::class.java) }
    private val disposables = CompositeDisposable()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.citiesCount.observe(this, Observer {
            text_view.text = "cities count: $it"
        })

        viewModel.error.observe(this, Observer {
            text_view.text = "error: ${it?.message}"
        })

        launch {
            disposables.add(viewModel.countCities(getDomain()))
        }

    }


    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
