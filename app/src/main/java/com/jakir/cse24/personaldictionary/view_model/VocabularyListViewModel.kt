package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.easyalert.EasyLog
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.data.repositories.VocabularyRepository
import kotlin.random.Random

class VocabularyListViewModel : ViewModel() {
    private val vocabularyRepository: VocabularyRepository by lazy { VocabularyRepository() }
    lateinit var vocabularies: MutableLiveData<ArrayList<Vocabulary>>

    fun getVocabularies() {
        vocabularies = MutableLiveData()
        vocabularyRepository.getVocabularies().observeForever {
            vocabularies.postValue(it)
        }
    }

    fun getFavouriteVocabularies(): MutableLiveData<ArrayList<Vocabulary>> {
        return vocabularyRepository.getFavouriteVocabularies()
    }

    fun removeItem(vocabulary: Vocabulary) {
        vocabularyRepository.deleteVocabulary(vocabulary.id);
    }

    private val reverseEnable: MutableLiveData<Boolean> = MutableLiveData(false)
    private val randomVocabulary: MutableLiveData<Vocabulary> = MutableLiveData()

    fun generateRandomVocabulary() {
        val randomIndex: Int? = vocabularies.value?.size?.let { Random.nextInt(0, it) }
        randomVocabulary.value = randomIndex?.let { vocabularies.value?.get(it) }
    }

    fun getRandomVocabulary(): LiveData<Vocabulary> {
        return randomVocabulary
    }

    fun getReverseEnable(): LiveData<Boolean> {
        return reverseEnable
    }

    fun setReverseEnable() {
        reverseEnable.value = !reverseEnable.value!!
    }

    fun addRemoveFavourite(id: String, status: Boolean): MutableLiveData<ResponseModel> {
        return vocabularyRepository.addRemoveFavourite(id, status)
    }

}