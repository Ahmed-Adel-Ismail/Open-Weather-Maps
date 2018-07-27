package com.ahmedismail.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.ahmedismail.app.domain.Domain
import com.ahmedismail.app.domain.database.database
import com.ahmedismail.app.domain.server.server
import kotlinx.coroutines.experimental.async

class App : Application() {

    val domain by lazy {
        async { Domain(server(this@App), database(this@App)) }
    }
}

fun Application.getDomain() = (this as App).domain
fun Activity.getDomain() = application.getDomain()
fun Fragment.getDomain() = activity?.application?.getDomain()
