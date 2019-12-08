package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.repositories.SignUpRepository
import com.jakir.cse24.personaldictionary.data.model.User

class SignUpViewModel : ViewModel() {
    var name: MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var phoneNumber: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var confirmPassword: MutableLiveData<String> = MutableLiveData()

    fun createAccount(user: User,pass: String): MutableLiveData<ResponseModel> {
//        return MutableLiveData<LoginModel>().apply { value = SignUpRepository().signUp(user) }
        return SignUpRepository()
            .signUp(user,pass)
    }
}