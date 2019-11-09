package com.jakir.cse24.personaldictionary.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseActivity
import com.jakir.cse24.personaldictionary.databinding.ActivityLoginBinding
import com.jakir.cse24.personaldictionary.model.LoginModel
import com.jakir.cse24.personaldictionary.model.User
import com.jakir.cse24.personaldictionary.view_model.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    override fun getContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        settingActionBar(getString(R.string.login), false)
        viewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]

        binding.loginViewModel = viewModel

        btnLogin.setOnClickListener {
            val phone = viewModel.phoneNumber.value
            val password = viewModel.password.value

            if (phone == null || phone == "") {
                binding.etPhone.error = getString(R.string.phone_hint)
                binding.etPhone.requestFocus()
                return@setOnClickListener
            }
            if (phone?.count() != 11) {
                binding.etPhone.error = getString(R.string.phone_validation_error)
                binding.etPhone.requestFocus()
                return@setOnClickListener
            }
            if (password == null || password == "") {
                binding.etPassword.error = getString(R.string.password_hint)
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            showProgressDialog("Checking...")
            viewModel.login(User(phone, password)).observe(this, Observer<LoginModel> {
                hideProgressDialog()
                if (it.status) {
                    showToast("login successful!")
                } else {
                    showToast(it.message)
                }
            })
        }

        tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }
    }
}