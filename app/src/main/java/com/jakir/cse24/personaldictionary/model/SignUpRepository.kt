package com.jakir.cse24.personaldictionary.model

import androidx.lifecycle.MutableLiveData

class SignUpRepository {
    fun signUp(user: User):LoginModel{
        if (user.name == "jakir"){
            return LoginModel(true, "Account creation successful!")
        }
        return LoginModel(false, "This phone number is already registered! use another phone number.")
    }
}