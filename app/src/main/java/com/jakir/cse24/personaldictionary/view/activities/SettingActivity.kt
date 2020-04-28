package com.jakir.cse24.personaldictionary.view.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseActivity
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.databinding.ActivitySettingBinding
import com.jakir.cse24.personaldictionary.utils.LocalHelper
import com.jakir.cse24.personaldictionary.view_model.SettingViewModel
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: ActivitySettingBinding
    override fun getContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[SettingViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        imvBack.setOnClickListener {
            finish()
        }

        viewModel.getCurrentLanguage().observe(this, Observer {
            if (PreferenceManager.currentLanguage != it){
                isLanguageChange = true
                PreferenceManager.currentLanguage = it
                LocalHelper.setLocale(this,PreferenceManager.currentLanguageCode)
                recreate()
            }
        })
    }
}
