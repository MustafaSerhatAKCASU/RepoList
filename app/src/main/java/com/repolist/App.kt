package com.repolist

import android.app.Application
import com.facebook.stetho.Stetho

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}