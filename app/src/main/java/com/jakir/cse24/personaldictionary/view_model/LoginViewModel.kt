package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.data.model.LoginModel
import com.jakir.cse24.personaldictionary.data.repositories.LoginRepository
import com.jakir.cse24.personaldictionary.data.model.User


class LoginViewModel : ViewModel() {
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    fun login(user: User): MutableLiveData<LoginModel> {
        return LoginRepository()
            .login(user)
    }
}