package com.jakir.cse24.personaldictionary.view.activities

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseActivity
import com.jakir.cse24.personaldictionary.databinding.ActivityAppInfoBinding
import com.jakir.cse24.personaldictionary.view_model.AppInfoViewModel
import com.jakir.cse24.personaldictionary.view_model.LoginViewModel
import kotlinx.android.synthetic.main.activity_app_info.*

class AppInfoActivity : BaseActivity() {
    private lateinit var viewModel: AppInfoViewModel
    private lateinit var binding: ActivityAppInfoBinding
    override fun getContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_app_info)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[AppInfoViewModel::class.java]
        viewModel.getAppInfo()
        viewModel.appinfo.observe(this, Observer {
            progressBar.visibility = View.GONE
            binding.data = it
        })
    }
}
