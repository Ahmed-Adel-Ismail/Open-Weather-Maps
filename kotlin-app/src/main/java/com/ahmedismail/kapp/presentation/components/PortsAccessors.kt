package com.ahmedismail.kapp.presentation.components

import android.app.Activity
import androidx.fragment.app.Fragment
import com.ahmedismail.kapp.domain.Ports
import kotlinx.coroutines.experimental.launch

fun Activity.withPorts(block: suspend Ports.() -> Unit) = launch {
    (application!! as Ports).block()
}

fun Fragment.withPorts(block: suspend Ports.() -> Unit) = launch {
    (activity?.application as? Ports)?.block()
}