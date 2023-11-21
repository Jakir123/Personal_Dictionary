package com.jakir.cse24.personaldictionary.data.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.jakir.cse24.personaldictionary.data.PreferenceManager
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
                    PreferenceManager.userId = firebaseAuth.currentUser!!.uid
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
            }
        return signUp
    }
}