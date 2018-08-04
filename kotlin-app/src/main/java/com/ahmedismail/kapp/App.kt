package com.ahmedismail.kapp

import android.app.Application
import com.ahmedismail.kapp.domain.Ports
import com.ahmedismail.kapp.domain.adapters.Adapters
import com.ahmedismail.kapp.domain.adapters.databaseAdapter
import com.ahmedismail.kapp.domain.adapters.serverAdapter
import com.ahmedismail.kapp.presentation.components.DisposablesLifeCycle
import com.ahmedismail.kapp.presentation.components.KeyboardHider
import kotlinx.coroutines.experimental.async

class App : Application(), Ports {

    override val adapters by lazy {
        async {
            Adapters(serverAdapter(this@App), databaseAdapter(this@App))
        }
    }


    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(DisposablesLifeCycle())
        registerActivityLifecycleCallbacks(KeyboardHider())
    }
}




