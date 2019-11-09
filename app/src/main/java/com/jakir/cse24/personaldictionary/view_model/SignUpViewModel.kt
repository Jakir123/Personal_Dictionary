package com.jakir.cse24.personaldictionary.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.model.User

class SignUpViewModel : ViewModel() {
    var name: MutableLiveData<String> = MutableLiveData()
    var phoneNumber: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var confirmPassword: MutableLiveData<String> = MutableLiveData()

    fun createAccount(user: User){
    }
}