package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.data.repositories.VocabularyRepository

class VocabularyListViewModel : ViewModel(){

    fun getVocabularies(): MutableLiveData<ArrayList<Vocabulary>>{
        return VocabularyRepository().getVocabularies()
    }

    fun removeItem(vocabulary: Vocabulary) {
        VocabularyRepository().deleteVocabulary(vocabulary.id);
    }
}