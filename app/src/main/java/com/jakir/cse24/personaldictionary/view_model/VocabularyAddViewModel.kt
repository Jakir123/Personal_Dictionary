package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.data.model.*

class VocabularyAddViewModel: ViewModel() {
    var word: MutableLiveData<String> = MutableLiveData()
    var meaning: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()
    var example: MutableLiveData<String> = MutableLiveData()


    fun addVocabulary(vocabulary: Vocabulary):  MutableLiveData<Boolean>{
        return MutableLiveData<Boolean>()
            .apply { value = VocabularyAddRepository().addVocabulary(vocabulary)}
    }
}