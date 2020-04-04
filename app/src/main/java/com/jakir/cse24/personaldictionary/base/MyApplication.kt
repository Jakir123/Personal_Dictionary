package com.jakir.cse24.personaldictionary.base

import androidx.multidex.MultiDexApplication
import com.jakir.cse24.personaldictionary.data.PreferenceManager

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        PreferenceManager.init(this)
    }
}