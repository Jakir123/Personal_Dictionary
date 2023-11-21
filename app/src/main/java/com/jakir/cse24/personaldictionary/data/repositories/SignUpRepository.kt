package com.jakir.cse24.personaldictionary.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.User

class SignUpRepository {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun signUp(user: User, pass: String): MutableLiveData<ResponseModel> {
        val signUp: MutableLiveData<ResponseModel> = MutableLiveData()
        firebaseAuth.createUserWithEmailAndPassword(user.email,pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    db.collection("users").document(firebaseAuth.uid!!).set(user).addOnSuccessListener {
                        PreferenceManager.userId = firebaseAuth.uid!!
                        signUp.value =
                            ResponseModel(
                                true,
                                "SignUp successful!"
                            )
                    }.addOnCanceledListener {

                    }.addOnFailureListener {

                    }
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