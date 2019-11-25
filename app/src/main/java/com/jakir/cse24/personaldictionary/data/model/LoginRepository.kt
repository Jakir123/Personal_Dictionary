package com.jakir.cse24.personaldictionary.data.model

class LoginRepository {
    fun login(user: User): LoginModel {
        if (user.phone == "01914228380")
            return LoginModel(true, "Login Successful!")
        return LoginModel(false, "Invalid credentials!")
    }
}