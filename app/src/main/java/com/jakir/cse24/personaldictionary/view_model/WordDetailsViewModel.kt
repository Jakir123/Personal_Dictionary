package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.data.repositories.FilterType
import com.jakir.cse24.personaldictionary.data.repositories.VocabularyRepository

class WordDetailsViewModel : ViewModel() {

    fun getVocabulary(id: String): MutableLiveData<ArrayList<Vocabulary>> {
        return VocabularyRepository().getVocabularies(FilterType.ALL)
    }

    fun removeVocabulary(id: String): MutableLiveData<ResponseModel> {
        return VocabularyRepository().deleteVocabulary(id)
    }

    fun addRemoveFavourite(id: String,status:Boolean): MutableLiveData<ResponseModel> {
        return VocabularyRepository().addRemoveFavourite(id,status)
    }
}