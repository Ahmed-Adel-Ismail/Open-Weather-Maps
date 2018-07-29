package com.ahmedismail.kapp.presentation.components

import android.app.Activity
import android.app.Application
import android.os.Bundle

class DisposablesLifeCycle : Application.ActivityLifecycleCallbacks {

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
        activity?.takeIf { it is DisposablesHolder }
                ?.let { it as DisposablesHolder }
                ?.disposables
                ?.apply { clear() }
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
        // do nothing
    }

    override fun onActivityStopped(p0: Activity?) {
        // do nothing
    }

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
        // do nothing
    }
}