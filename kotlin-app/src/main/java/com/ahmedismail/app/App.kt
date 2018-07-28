package com.ahmedismail.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.ahmedismail.app.domain.Ports
import com.ahmedismail.app.domain.adapters.Adapters
import com.ahmedismail.app.domain.adapters.databaseAdapter
import com.ahmedismail.app.domain.adapters.serverAdapter
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class App : Application(), Ports {

    override val adapters by lazy {
        async { Adapters(serverAdapter(this@App), databaseAdapter(this@App)) }
    }
}

fun Activity.withPorts(block: suspend Ports.() -> Unit) = launch {
    (application!! as Ports).block()
}

fun Fragment.withPorts(block: suspend Ports.() -> Unit) = launch {
    (activity?.application as? Ports)?.block()
}


