package com.jakir.cse24.personaldictionary.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class LoginRepository {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun login(user: User): MutableLiveData<LoginModel> {
        val signUp: MutableLiveData<LoginModel> = MutableLiveData()
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signUp.value = LoginModel(it.isSuccessful,"SignUp successful!")
                } else {
                    signUp.value = LoginModel(false,"SignUp failed!")
                }
            }.addOnCanceledListener {
                signUp.value = LoginModel(false,"Task $this was cancelled normally!")
            }.addOnFailureListener{
                signUp.value = LoginModel(false, it.message.toString())
                Log.e("SignUpRepository","OnFailureListener: ${it.message}")
                Log.e("SignUpRepository","OnFailureListener: ${it.localizedMessage}")
                Log.e("SignUpRepository","OnFailureListener: ${it.stackTrace}")
            }
        return signUp
    }
}