package com.jakir.cse24.personaldictionary

import android.content.Intent
import android.os.Bundle
import com.jakir.cse24.personaldictionary.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onViewReady(savedInstanceState: Bundle?) {
        // here initialize views

    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }
}
