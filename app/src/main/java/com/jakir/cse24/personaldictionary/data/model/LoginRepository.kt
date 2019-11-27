package com.jakir.cse24.personaldictionary.data.model

class LoginRepository {
    fun login(user: User): LoginModel {
        if (user.email == "jakir.cse24@gmail.com")
            return LoginModel(true, "Login Successful!")
        return LoginModel(false, "Invalid credentials!")
    }
}