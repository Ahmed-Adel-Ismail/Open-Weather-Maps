package com.ahmedismail.kapp.presentation.components

import android.app.Activity
import android.app.Application
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.ahmedismail.kapp.R

class KeyboardHider : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(p0: Activity?) {
        // do nothing
    }

    override fun onActivityResumed(p0: Activity?) {
        // do nothing
    }

    override fun onActivityStarted(p0: Activity?) {
        // do nothing
    }

    override fun onActivityDestroyed(activity: Activity?) {
        // do nothing
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
        // do nothing
    }

    override fun onActivityStopped(p0: Activity?) {
        // do nothing
    }

    override fun onActivityCreated(activity: Activity?, p1: Bundle?) {
        activity?.findViewById<ViewGroup>(R.id.container)
                ?.setOnTouchListener { _, _ ->
                    activity.getSystemService(INPUT_METHOD_SERVICE)
                            ?.let { it as InputMethodManager }
                            ?.takeIf { activity.currentFocus != null }
                            ?.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
                    return@setOnTouchListener false
                }
    }
}
