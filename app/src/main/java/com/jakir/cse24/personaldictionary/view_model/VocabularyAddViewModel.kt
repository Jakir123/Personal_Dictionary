package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.data.repositories.VocabularyRepository

class VocabularyAddViewModel : ViewModel() {
    var word = MutableLiveData<String>()
    var meaning = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var example = MutableLiveData<String>()
    var synonyms = MutableLiveData<String>()
    var antonyms = MutableLiveData<String>()


    fun addVocabulary(vocabulary: Vocabulary): MutableLiveData<ResponseModel> {
        return VocabularyRepository()
            .addVocabulary(vocabulary)
    }
}