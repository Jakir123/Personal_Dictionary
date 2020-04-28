package com.jakir.cse24.personaldictionary.view_model

import android.app.AlertDialog
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.repositories.SettingRepository


class SettingViewModel : ViewModel() {
    private val settingRepository: SettingRepository by lazy { SettingRepository() }
    private val currentLanguage: MutableLiveData<String> =  MutableLiveData(PreferenceManager.currentLanguage)
    private val currentTheme: MutableLiveData<String> = MutableLiveData(PreferenceManager.currentTheme)
    private val currentRingTone: MutableLiveData<String> = MutableLiveData(PreferenceManager.currentNotificationTone)

    fun languageSelectionPressed(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
        builder.setTitle(view.context.getString(R.string.language_selection))
        builder.setSingleChoiceItems(
            settingRepository.languages, PreferenceManager.currentLanguagePosition
        ) { dialog, position ->
            if (PreferenceManager.currentLanguagePosition != position) {
                // handle language selection
                PreferenceManager.currentLanguagePosition = position
                PreferenceManager.currentLanguage = settingRepository.languages[position]
                currentLanguage.value = PreferenceManager.currentLanguage

            }
            dialog.dismiss()
        }
        builder.create().show()
    }

    /**
     * Expose LiveData if you do not use two-way data binding
     */
    fun getCurrentLanguage(): LiveData<String> {
        return currentLanguage
    }

    fun getCurrentTheme(): LiveData<String> {
        return currentTheme
    }

    fun getCurrentRingTone(): LiveData<String> {
        return currentRingTone
    }

//    /**
//     * Expose MutableLiveData to use two-way data binding
//     */
//    fun getLastName(): MutableLiveData<String> {
//        return lastName
//    }

    fun themeSelectionPressed() {

    }

    fun notificationToneSelectionPressed() {

    }

    fun getNotificationStatus(): Boolean {
        return PreferenceManager.isNotificationActive
    }

    fun setNotificationStatus(value: Boolean) {
        if (PreferenceManager.isNotificationActive != value) {
            // do other stuff
            PreferenceManager.isNotificationActive = value
        }
    }

    fun getVibrationStatus(): Boolean {
        return PreferenceManager.isVibrationActive
    }

    fun setVibrationStatus(value: Boolean) {
        if (PreferenceManager.isVibrationActive != value) {
            // do other stuff
            PreferenceManager.isVibrationActive = value
        }
    }
}
