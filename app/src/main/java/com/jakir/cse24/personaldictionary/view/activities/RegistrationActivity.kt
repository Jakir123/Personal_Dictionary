package com.jakir.cse24.personaldictionary.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseActivity
import com.jakir.cse24.personaldictionary.databinding.ActivityRegistrationBinding
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.User
import com.jakir.cse24.personaldictionary.view_model.SignUpViewModel
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : BaseActivity() {
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: ActivityRegistrationBinding

    private lateinit var mAuth: FirebaseAuth

    override fun getContentView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registration)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
//        settingActionBar(getString(R.string.sign_up),true)
        viewModel = ViewModelProviders.of(this)[SignUpViewModel::class.java]
        binding.viewModel = viewModel

        mAuth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            val name = viewModel.name.value
            val email = viewModel.email.value
            val phone = viewModel.phoneNumber.value
            val pass = viewModel.password.value
            val confirmPass = viewModel.confirmPassword.value

            if (name == null || name == ""){
                binding.etName.error = getString(R.string.name_hint)
                binding.etName.requestFocus()
                return@setOnClickListener
            }
            if (email == null || email == ""){
                binding.etEmail.error = getString(R.string.email_hint)
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if(!isEmailValid(email) ){
                binding.etEmail.error = getString(R.string.email_validation_error)
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (phone == null || phone == ""){
                binding.etPhone.error = getString(R.string.phone_hint)
                binding.etPhone.requestFocus()
                return@setOnClickListener
            }
            if(phone.count() != 11 ){
                binding.etPhone.error = getString(R.string.phone_validation_error)
                binding.etPhone.requestFocus()
                return@setOnClickListener
            }
            if (pass == null || pass == ""){
                binding.etPassword.error = getString(R.string.password_hint)
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            if (confirmPass == null || confirmPass == ""){
                binding.etConfirmPassword.error = getString(R.string.password_confirm_hint)
                binding.etConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            if (pass != confirmPass){
                binding.etConfirmPassword.error = getString(R.string.password_confirm_mismatch_error)
                binding.etConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            showProgressDialog("Request is in progress ...")
            viewModel.createAccount(User(name,email,phone),pass).observe(this, Observer<ResponseModel> {
                hideProgressDialog()
                if (it.status) {
                    startActivity(
                        Intent(this@RegistrationActivity,
                            DashboardActivity::class.java )
                    )
                } else {
                    showToast(it.message)
                }
            })

        }

        tvLogin.setOnClickListener { finish() }
    }

}
