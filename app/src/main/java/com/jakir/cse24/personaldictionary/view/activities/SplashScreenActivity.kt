package com.jakir.cse24.personaldictionary.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseActivity
import com.jakir.cse24.personaldictionary.data.FirebaseSource
import com.jakir.cse24.personaldictionary.data.PreferenceManager

class SplashScreenActivity : BaseActivity() {
    override fun getContentView() {
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000) // 3 sec
    }
}
