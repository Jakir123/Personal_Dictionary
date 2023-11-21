package com.jakir.cse24.personaldictionary.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.jakir.cse24.personaldictionary.base.BaseRepository
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

class VocabularyRepository : BaseRepository() {
    private var nextItem = 1

    @SuppressLint("LongLogTag")
    fun getVocabularies(filterType: FilterType): MutableLiveData<ArrayList<Vocabulary>> {
        val vocabularies: MutableLiveData<ArrayList<Vocabulary>> = MutableLiveData()
        when(filterType){
            FilterType.ALL->{
                vocabularyCollection.whereEqualTo("userId", PreferenceManager.userId)
                    .orderBy("timeStamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { value, exception ->
                        if (exception != null) {

                            return@addSnapshotListener
                        }
                        val items = ArrayList<Vocabulary>()
                        for (doc in value!!) {
                            items.add(doc.toObject(Vocabulary::class.java))
                        }
                        vocabularies.value = items
                    }
            }
            FilterType.SEVEN_DAYS->{
                vocabularyCollection.whereEqualTo("userId", PreferenceManager.userId)
                    .whereLessThan("timeStamp", Timestamp.now()).whereGreaterThan("timeStamp",Timestamp(getDaysAgo(7)))
                    .orderBy("timeStamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { value, exception ->
                        if (exception != null) {

                            return@addSnapshotListener
                        }
                        val items = ArrayList<Vocabulary>()
                        for (doc in value!!) {
                            items.add(doc.toObject(Vocabulary::class.java))
                        }
                        vocabularies.value = items
                    }
            }
            FilterType.THIRTY_DAYS->{
                vocabularyCollection.whereEqualTo("userId", PreferenceManager.userId)
                    .whereLessThan("timeStamp", Timestamp.now()).whereGreaterThan("timeStamp",Timestamp(getDaysAgo(31)))
                    .orderBy("timeStamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { value, exception ->
                        if (exception != null) {

                            return@addSnapshotListener
                        }
                        val items = ArrayList<Vocabulary>()
                        for (doc in value!!) {
                            items.add(doc.toObject(Vocabulary::class.java))
                        }
                        vocabularies.value = items
                    }
            }
            FilterType.THREE_MONTHS->{
                vocabularyCollection.whereEqualTo("userId", PreferenceManager.userId)
                    .whereLessThan("timeStamp", Timestamp.now()).whereGreaterThan("timeStamp",Timestamp(getDaysAgo(92)))
                    .orderBy("timeStamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { value, exception ->
                        if (exception != null) {

                            return@addSnapshotListener
                        }
                        val items = ArrayList<Vocabulary>()
                        for (doc in value!!) {
                            items.add(doc.toObject(Vocabulary::class.java))
                        }
                        vocabularies.value = items
                    }
            }
            FilterType.SIX_MONTHS->{
                vocabularyCollection.whereEqualTo("userId", PreferenceManager.userId)
                    .whereLessThan("timeStamp", Timestamp.now()).whereGreaterThan("timeStamp",Timestamp(getDaysAgo(183)))
                    .orderBy("timeStamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { value, exception ->
                        if (exception != null) {

                            return@addSnapshotListener
                        }
                        val items = ArrayList<Vocabulary>()
                        for (doc in value!!) {
                            items.add(doc.toObject(Vocabulary::class.java))
                        }
                        vocabularies.value = items
                    }
            }
            FilterType.ONE_YEAR->{
                vocabularyCollection.whereEqualTo("userId", PreferenceManager.userId)
                    .whereLessThan("timeStamp", Timestamp.now()).whereGreaterThan("timeStamp",Timestamp(getDaysAgo(366)))
                    .orderBy("timeStamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { value, exception ->
                        if (exception != null) {

                            return@addSnapshotListener
                        }
                        val items = ArrayList<Vocabulary>()
                        for (doc in value!!) {
                            items.add(doc.toObject(Vocabulary::class.java))
                        }
                        vocabularies.value = items
                    }
            }
        }
        return vocabularies
    }

    fun getFavouriteVocabularies(pageSize: Int = 20): MutableLiveData<ArrayList<Vocabulary>> {
        val items = ArrayList<Vocabulary>()
        val vocabularies: MutableLiveData<ArrayList<Vocabulary>> = MutableLiveData()
        vocabularyCollection.whereEqualTo("userId", PreferenceManager.userId)
            .whereEqualTo("favourite", true)
            .get()
            .addOnCompleteListener { task ->
                task.result?.run {
                    for (doc in documents) {
                        items.add(doc.toObject(Vocabulary::class.java)!!)
                    }
                    vocabularies.value = items
                }
            }.addOnFailureListener {

            }
        return vocabularies
    }


    fun addVocabulary(vocabulary: Vocabulary): MutableLiveData<ResponseModel> {
        val response: MutableLiveData<ResponseModel> = MutableLiveData()
        val docRef = vocabularyCollection.document()
        vocabulary.userId = userId
        vocabulary.id = docRef.id

        docRef.set(vocabulary).addOnSuccessListener {
            response.value = ResponseModel(true, "New vocabulary added")
        }.addOnFailureListener {
            response.value = ResponseModel(false, it.message.toString())
        }.addOnCanceledListener {
            response.value = ResponseModel(false, "Request canceled!")
        }
        return response
    }

    fun addRemoveFavourite(id: String, status: Boolean): MutableLiveData<ResponseModel> {
        val response = MutableLiveData<ResponseModel>()
        vocabularyCollection.document(id).update("favourite", status).addOnFailureListener {
//            EasyLog.logE("Update failed: ${it.localizedMessage}") Todo Need to fix
            response.value = ResponseModel(false, it.message.toString())
        }.addOnSuccessListener {
            response.value = ResponseModel(true, "Vocabulary updated.")
        }.addOnCanceledListener {
            response.value = ResponseModel(false, "Request canceled!")
        }
        return response
    }

    fun deleteVocabulary(id: String): MutableLiveData<ResponseModel> {
        val response = MutableLiveData<ResponseModel>()
        vocabularyCollection.document(id).delete().addOnCompleteListener {
            response.value = ResponseModel(true, "Vocabulary deleted.")
        }.addOnFailureListener {
            response.value = ResponseModel(false, it.message.toString())
        }.addOnCanceledListener {
            response.value = ResponseModel(false, "Request canceled!")
        }
        return response
    }

    fun updateVocabulary(
        id: String,
        vocabulary: MutableMap<String, Any>
    ): MutableLiveData<ResponseModel> {
        val response = MutableLiveData<ResponseModel>()
        vocabularyCollection.document(id).update(vocabulary).addOnFailureListener {
            response.value = ResponseModel(false, it.message.toString())
        }.addOnSuccessListener {
            response.value = ResponseModel(true, "Vocabulary updated.")
        }.addOnCanceledListener {
            response.value = ResponseModel(false, "Request canceled!")
        }
        return response
    }
}

enum class FilterType{
    ALL,
    SEVEN_DAYS,
    THIRTY_DAYS,
    THREE_MONTHS,
    SIX_MONTHS,
    ONE_YEAR
}