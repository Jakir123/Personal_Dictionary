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

    fun updateVocabulary(id: String, vocabulary: Vocabulary): MutableLiveData<ResponseModel> {
        val data = mutableMapOf<String, Any>()
        data["word"] = vocabulary.word
        data["type"] = vocabulary.type
        data["synonyms"] = vocabulary.synonyms
        data["antonyms"] = vocabulary.antonyms
        data["translation"] = vocabulary.translation
        return VocabularyRepository().updateVocabulary(id, data)
    }

    fun setValue(vocabulary: Vocabulary) {
        word.value = vocabulary.word
        meaning.value = vocabulary.translation.meaning
        description.value = vocabulary.translation.description
        example.value = vocabulary.translation.example
        synonyms.value = vocabulary.synonyms
        antonyms.value = vocabulary.antonyms
    }
}