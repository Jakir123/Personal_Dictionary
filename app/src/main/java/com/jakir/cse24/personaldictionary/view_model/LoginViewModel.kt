package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.model.LoginModel
import com.jakir.cse24.personaldictionary.model.LoginRepository
import com.jakir.cse24.personaldictionary.model.User


class LoginViewModel : ViewModel() {
    var phoneNumber: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    fun login(user: User):  MutableLiveData<LoginModel>{
       return MutableLiveData<LoginModel>()
           .apply { value = LoginRepository().login(user)}
    }
}