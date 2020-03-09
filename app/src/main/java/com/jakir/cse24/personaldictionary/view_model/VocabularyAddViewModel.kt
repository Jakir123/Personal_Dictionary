package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.model.*
import com.jakir.cse24.personaldictionary.data.repositories.VocabularyAddRepository

class VocabularyAddViewModel: ViewModel() {
    var word: MutableLiveData<String> = MutableLiveData()
    var meaning: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()
    var example: MutableLiveData<String> = MutableLiveData()


    fun addVocabulary(vocabulary: Vocabulary):  MutableLiveData<ResponseModel>{
        return VocabularyAddRepository()
                .addVocabulary(vocabulary)
    }
}