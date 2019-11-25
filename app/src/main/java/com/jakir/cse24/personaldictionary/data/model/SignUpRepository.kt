package com.jakir.cse24.personaldictionary.data.model

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignUpRepository {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun signUp(user: User): LoginModel {
        return LoginModel(false, "")
    }

    suspend fun registration(user: User) =
        withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        LoginModel(true, "Account creation successful!")
                    } else {
                        LoginModel(false, "" + it.exception)
                    }
                }
        }
}