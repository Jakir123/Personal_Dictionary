package com.jakir.cse24.personaldictionary.data.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jakir.cse24.easyalert.EasyLog
import com.jakir.cse24.easyalert.EasyToast
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

class VocabularyAddRepository {
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun addVocabulary(vocabulary: Vocabulary): MutableLiveData<ResponseModel> {
        val response: MutableLiveData<ResponseModel> = MutableLiveData()
        EasyLog.logE(FirebaseAuth.getInstance().currentUser!!.uid,"VocabularyAddRepository")
        vocabulary.userId = PreferenceManager.userId
        db.collection("vocabularies").add(vocabulary).addOnSuccessListener {
            response.value = ResponseModel(true,"New vocabulary added")
        }.addOnFailureListener {
            response.value = ResponseModel(false,it.message.toString())
        }.addOnCanceledListener {
            response.value = ResponseModel(false, "Request canceled!")
        }
        return response
    }
}