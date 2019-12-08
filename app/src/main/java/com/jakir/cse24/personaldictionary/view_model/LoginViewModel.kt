package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.repositories.LoginRepository


class LoginViewModel : ViewModel() {
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    fun login(email: String, password: String): MutableLiveData<ResponseModel> {
        return LoginRepository()
            .login(email,password)
    }
}