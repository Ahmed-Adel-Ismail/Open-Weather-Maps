package com.ahmedismail.kapp

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.ahmedismail.kapp.domain.Ports
import com.ahmedismail.kapp.domain.adapters.Adapters
import com.ahmedismail.kapp.domain.adapters.databaseAdapter
import com.ahmedismail.kapp.domain.adapters.serverAdapter
import com.ahmedismail.kapp.presentation.components.DisposablesLifeCycle
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class App : Application(), Ports {

    override val adapters by lazy {
        async {
            Adapters(serverAdapter(this@App), databaseAdapter(this@App))
        }
    }


    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(DisposablesLifeCycle())
    }
}




