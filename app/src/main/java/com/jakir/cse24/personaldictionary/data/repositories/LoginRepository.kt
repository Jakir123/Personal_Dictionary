package com.jakir.cse24.personaldictionary.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.jakir.cse24.personaldictionary.data.model.ResponseModel

class LoginRepository {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun login(email: String, password: String): MutableLiveData<ResponseModel> {
        val signUp: MutableLiveData<ResponseModel> = MutableLiveData()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signUp.value =
                        ResponseModel(
                            it.isSuccessful,
                            "SignUp successful!"
                        )
                } else {
                    signUp.value =
                        ResponseModel(
                            false,
                            "SignUp failed!"
                        )
                }
            }.addOnCanceledListener {
                signUp.value =
                    ResponseModel(
                        false,
                        "Task $this was cancelled normally!"
                    )
            }.addOnFailureListener {
                signUp.value =
                    ResponseModel(
                        false,
                        it.message.toString()
                    )
                Log.e("SignUpRepository", "OnFailureListener: ${it.message}")
                Log.e("SignUpRepository", "OnFailureListener: ${it.localizedMessage}")
                Log.e("SignUpRepository", "OnFailureListener: ${it.stackTrace}")
            }
        return signUp
    }
}