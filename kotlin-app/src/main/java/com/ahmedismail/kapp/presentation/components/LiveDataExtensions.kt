package com.ahmedismail.kapp.presentation.components

import androidx.lifecycle.MutableLiveData



fun <T> MutableLiveData<T>.withValue(value: T): MutableLiveData<T> {
    this.value = value
    return this
}