package com.jakir.cse24.personaldictionary.data.repositories

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.jakir.cse24.easyalert.EasyLog
import com.jakir.cse24.personaldictionary.base.BaseRepository
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.model.Translation
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

class VocabularyListRepository :BaseRepository(){
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

    fun deleteVocabulary(vocabulary: Vocabulary){
        vocabularyCollection.document(vocabulary.id).delete().addOnCompleteListener {

        }

    }
}