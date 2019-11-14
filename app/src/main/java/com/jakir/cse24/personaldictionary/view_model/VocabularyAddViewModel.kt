package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VocabularyAddViewModel: ViewModel() {
    var word: MutableLiveData<String> = MutableLiveData()
    var meaning: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()
    var example: MutableLiveData<String> = MutableLiveData()
}