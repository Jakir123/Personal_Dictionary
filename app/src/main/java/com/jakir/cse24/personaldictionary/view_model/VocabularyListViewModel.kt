package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.data.repositories.VocabularyRepository
import kotlin.random.Random

class VocabularyListViewModel : ViewModel() {
    private val vocabularyRepository: VocabularyRepository by lazy { VocabularyRepository() }

    lateinit var vocabularies: MutableLiveData<ArrayList<Vocabulary>>

    fun getVocabularies() {
        vocabularies = vocabularyRepository.getVocabularies()
    }

    fun getFavouriteVocabularies(): MutableLiveData<ArrayList<Vocabulary>> {
        return vocabularyRepository.getFavouriteVocabularies()
    }

    fun removeItem(vocabulary: Vocabulary) {
        vocabularyRepository.deleteVocabulary(vocabulary.id);
    }

    val randomVocabulary = MutableLiveData<Vocabulary>()

    fun generateRandomVocabulary(){
        val randomIndex: Int? = vocabularies.value?.size?.let { Random.nextInt(0, it) }
        randomVocabulary.apply {
           postValue(randomIndex?.let { vocabularies.value?.get(it) })
        }
    }

    fun addRemoveFavourite(id: String,status:Boolean): MutableLiveData<ResponseModel> {
        return vocabularyRepository.addRemoveFavourite(id,status)
    }

}