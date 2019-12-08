package com.jakir.cse24.personaldictionary.base

import android.app.Application
import com.jakir.cse24.personaldictionary.data.PreferenceManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceManager.init(this)
    }
}