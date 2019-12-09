package com.jakir.cse24.personaldictionary.data.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.model.Translation
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

class VocabularyListRepository {
    private var nextItem = 1
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    @SuppressLint("LongLogTag")
    fun getVocabularies(pageSize: Int = 20): List<Vocabulary> {
        val items = mutableListOf<Vocabulary>()

        db.collection("vocabularies").get().addOnSuccessListener {
//            for (document in it.documents) {
//                items.add(document.toObject(Vocabulary::class.java)!!)
//            }
        }.addOnFailureListener {
            Log.e(
                "VocabularyListRepository",
                "Exception in getVocabularies: ${it.localizedMessage}"
            )
        }

//        val lastItem = nextItem + pageSize - 1
//
//        for (i in nextItem..lastItem) {
//            items.add("Item $i")
//        }

//        nextItem = lastItem + 1
//        items.add(
//            Vocabulary(
//                "Vulnerability",
//                "Noun",
//                Translation(
//                    "Durbolota",
//                    "who is weak",
//                    "he is vulnerable then him!"
//                )
//            )
//        )
//        items.add(
//            Vocabulary(
//                "Despite",
//                "Noun",
//                Translation(
//                    "Sotteo",
//                    "Despite",
//                    "Despite i am sick i want to play!"
//                )
//            )
//        )
//        items.add(
//            Vocabulary(
//                "Despite",
//                "Noun",
//                Translation(
//                    "Sotteo",
//                    "Despite",
//                    "Despite i am sick i want to play!"
//                )
//            )
//        )
//        items.add(
//            Vocabulary(
//                "Despite",
//                "Noun",
//                Translation(
//                    "Sotteo",
//                    "Despite",
//                    "Despite i am sick i want to play!"
//                )
//            )
//        )
//        items.add(
//            Vocabulary(
//                "Despite",
//                "Pronoun",
//                Translation(
//                    "Sotteo",
//                    "Despite",
//                    "Despite i am sick i want to play!"
//                )
//            )
//        )

        return items
    }
}