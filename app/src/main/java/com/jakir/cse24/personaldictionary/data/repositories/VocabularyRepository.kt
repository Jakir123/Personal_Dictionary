package com.jakir.cse24.personaldictionary.data.repositories

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.jakir.cse24.easyalert.EasyLog
import com.jakir.cse24.personaldictionary.base.BaseRepository
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.Translation
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

class VocabularyRepository :BaseRepository(){
    private var nextItem = 1

    @SuppressLint("LongLogTag")
    fun getVocabularies(pageSize: Int = 20): MutableLiveData<ArrayList<Vocabulary>> {
        val items = ArrayList<Vocabulary>()
        val vocabularies: MutableLiveData<ArrayList<Vocabulary>> = MutableLiveData()
        vocabularyCollection.whereEqualTo("userId",PreferenceManager.userId)
            .get()
            .addOnCompleteListener{task ->
                task.result?.run {
                    for (doc in documents){
                        items.add(doc.toObject(Vocabulary::class.java)!!)
                    }
                    vocabularies.value = items
                }
            }.addOnFailureListener {
            EasyLog.logE(
                "Exception in getVocabularies: ${it.localizedMessage}",
                "VocabularyListRepository"
            )
        }

//        val lastItem = nextItem + pageSize - 1

        return vocabularies
    }

    fun getFavouriteVocabularies(pageSize: Int = 20): MutableLiveData<ArrayList<Vocabulary>> {
        val items = ArrayList<Vocabulary>()
        val vocabularies: MutableLiveData<ArrayList<Vocabulary>> = MutableLiveData()
        vocabularyCollection.whereEqualTo("userId",PreferenceManager.userId)
            .whereEqualTo("favourite", true)
            .get()
            .addOnCompleteListener{task ->
                task.result?.run {
                    for (doc in documents){
                        items.add(doc.toObject(Vocabulary::class.java)!!)
                    }
                    vocabularies.value = items
                }
            }.addOnFailureListener {
                EasyLog.logE(
                    "Exception in getVocabularies: ${it.localizedMessage}",
                    "VocabularyListRepository"
                )
            }
        return vocabularies
    }


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

    fun addRemoveFavourite(id: String,status: Boolean):MutableLiveData<ResponseModel>{
        val response = MutableLiveData<ResponseModel>()
        vocabularyCollection.document(id).update("favourite",status).addOnFailureListener {
            EasyLog.logE("Update failed: ${it.localizedMessage}")
            response.value = ResponseModel(false,it.message.toString())
        }.addOnSuccessListener {
            response.value = ResponseModel(true,"Vocabulary updated.")
        }.addOnCanceledListener {
            response.value = ResponseModel(false, "Request canceled!")
        }
        return response
    }

    fun deleteVocabulary(id:String):MutableLiveData<ResponseModel>{
        val response = MutableLiveData<ResponseModel>()
        vocabularyCollection.document(id).delete().addOnCompleteListener {
              response.value = ResponseModel(true,"Vocabulary deleted.")
        }.addOnFailureListener {
            response.value = ResponseModel(false,it.message.toString())
        }.addOnCanceledListener {
            response.value = ResponseModel(false, "Request canceled!")
        }
        return response
    }

    fun updateVocabulary(id: String,vocabulary: MutableMap<String,Any>):MutableLiveData<ResponseModel>{
        val response = MutableLiveData<ResponseModel>()
        vocabularyCollection.document(id).update(vocabulary).addOnFailureListener {
            EasyLog.logE("Update failed: ${it.localizedMessage}")
            response.value = ResponseModel(false,it.message.toString())
        }.addOnSuccessListener {
            response.value = ResponseModel(true,"Vocabulary updated.")
        }.addOnCanceledListener {
            response.value = ResponseModel(false, "Request canceled!")
        }
        return response
    }
}