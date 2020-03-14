package com.jakir.cse24.personaldictionary.data.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jakir.cse24.easyalert.EasyLog
import com.jakir.cse24.easyalert.EasyToast
import com.jakir.cse24.personaldictionary.base.BaseRepository
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

class VocabularyAddRepository: BaseRepository() {

    fun addVocabulary(vocabulary: Vocabulary): MutableLiveData<ResponseModel> {
        val response: MutableLiveData<ResponseModel> = MutableLiveData()

        val docRef = vocabularyCollection.document()
        vocabulary.userId = userId
        vocabulary.id = docRef.id

        docRef.set(vocabulary).addOnSuccessListener {
            response.value = ResponseModel(true,"New vocabulary added")
        }.addOnFailureListener {
            response.value = ResponseModel(false,it.message.toString())
        }.addOnCanceledListener {
            response.value = ResponseModel(false, "Request canceled!")
        }
        return response
    }
}