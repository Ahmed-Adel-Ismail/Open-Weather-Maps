package com.ahmedismail.kapp.presentation.components

import androidx.lifecycle.MutableLiveData

val <T> T.toMutableLiveData: MutableLiveData<T>
    get() = MutableLiveData<T>().default(this)

private fun <T> MutableLiveData<T>.default(value: T): MutableLiveData<T> {
    this.value = value
    return this
}

