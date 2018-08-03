package com.ahmedismail.kapp.presentation.components

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

fun TextView.withTextWatcher(afterTextChanged: (String) -> Unit) {

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            afterTextChanged(text?.toString().orEmpty())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }
    })
}