package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.ViewModel
import com.jakir.cse24.easyalert.EasyLog
import com.jakir.cse24.personaldictionary.data.repositories.SettingRepository


class SettingViewModel : ViewModel() {
    private val settingRepository: SettingRepository by lazy { SettingRepository() }

    //    lateinit var selectedLanguage: MutableLiveData<String>
//    lateinit var selectedTheme: MutableLiveData<String>
//    lateinit var selectedRingTone: MutableLiveData<String>
    var selectedLanguage: String = "English"
    var selectedTheme: String = "Classic"
    var selectedRingTone: String = "Default"
    var notificationStatus: Boolean = true
    var vibrationStatus: Boolean = false

    fun languageSelectionPressed() {
    }
}