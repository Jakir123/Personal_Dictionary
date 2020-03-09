package com.jakir.cse24.personaldictionary.data.repositories

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.jakir.cse24.easyalert.EasyLog
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.model.Translation
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

class VocabularyListRepository {
    private var nextItem = 1
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    @SuppressLint("LongLogTag")
    fun getVocabularies(pageSize: Int = 20): MutableLiveData<List<Vocabulary>> {
        val items = mutableListOf<Vocabulary>()
        val vocabularies: MutableLiveData<List<Vocabulary>> = MutableLiveData()
        db.collection("vocabularies")
            .whereEqualTo("userId",PreferenceManager.userId)
            .get()
            .addOnCompleteListener{task ->
                task.result?.run {
                    for (doc in documents){
                        items.add(doc.toObject(Vocabulary::class.java)!!)
                    }
                    vocabularies.value = items
                }
            }
        db.collection("vocabularies").get().addOnSuccessListener {
            //            for (document in it.documents) {
//                items.add(document.toObject(Vocabulary::class.java)!!)
//            }
        }.addOnFailureListener {
            EasyLog.logE(
                "Exception in getVocabularies: ${it.localizedMessage}",
                "VocabularyListRepository"
            )
        }

//        val lastItem = nextItem + pageSize - 1

        return vocabularies
    }
}