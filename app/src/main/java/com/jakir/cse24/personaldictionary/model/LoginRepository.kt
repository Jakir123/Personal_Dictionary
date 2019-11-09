package com.jakir.cse24.personaldictionary.model

class LoginRepository {
    fun login(user: User): LoginModel {
        return LoginModel(false,"Invalid credentials!")
    }
}