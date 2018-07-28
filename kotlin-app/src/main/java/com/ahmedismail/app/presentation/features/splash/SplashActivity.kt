package com.ahmedismail.app.presentation.features.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ahmedismail.app.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(SplashViewModel::class.java) }
    val disposables = CompositeDisposable()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.citiesCount.observe(this, Observer {
            splash_label.text = "cities count: $it"
        })

        viewModel.error.observe(this, Observer {
            val message = it?.message ?: ""
            splash_label.text = "error: $message"
        })

        viewModel.loading.observe(this, Observer {
            splash_progress.visibility = if (it) VISIBLE else GONE
        })

        loadCitiesCount()
    }


    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
