package com.ahmedismail.kapp.presentation.components

import io.reactivex.disposables.CompositeDisposable

interface DisposablesHolder {

    val disposables: CompositeDisposable

}