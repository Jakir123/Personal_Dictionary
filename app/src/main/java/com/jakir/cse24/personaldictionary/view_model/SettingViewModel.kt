package com.jakir.cse24.personaldictionary.view_model

import android.app.AlertDialog
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.repositories.SettingRepository
import com.jakir.cse24.personaldictionary.utils.LocalHelper


class SettingViewModel : ViewModel() {
    private val settingRepository: SettingRepository by lazy { SettingRepository() }
    private val currentLanguage: MutableLiveData<String> =  MutableLiveData(PreferenceManager.currentLanguage)
    private val currentTheme: MutableLiveData<String> = MutableLiveData(PreferenceManager.currentTheme)
    private val currentNotificationTone: MutableLiveData<String> = MutableLiveData(PreferenceManager.currentNotificationTone)

    fun languageSelectionPressed(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
        builder.setTitle(view.context.getString(R.string.language_selection))
        builder.setSingleChoiceItems(
            settingRepository.languages, PreferenceManager.currentLanguagePosition
        ) { dialog, position ->
            if (PreferenceManager.currentLanguagePosition != position) {
                // handle language selection
                PreferenceManager.currentLanguagePosition = position
                PreferenceManager.currentLanguageCode = settingRepository.languageCodes[position]
                currentLanguage.value = settingRepository.languages[position]
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
        return currentNotificationTone
    }

//    /**
//     * Expose MutableLiveData to use two-way data binding
//     */
//    fun getLastName(): MutableLiveData<String> {
//        return lastName
//    }

    fun themeSelectionPressed(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
        builder.setTitle(view.context.getString(R.string.theme_selection))
        builder.setSingleChoiceItems(
            settingRepository.themes, PreferenceManager.currentThemePosition
        ) { dialog, position ->
            if (PreferenceManager.currentThemePosition != position) {
                // handle language selection
                PreferenceManager.currentThemePosition = position
                currentTheme.value = settingRepository.themes[position]
            }
            dialog.dismiss()
        }
        builder.create().show()
    }

    fun notificationToneSelectionPressed(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
        builder.setTitle(view.context.getString(R.string.notification_tone_selection))
        builder.setSingleChoiceItems(
            settingRepository.notificationTones, PreferenceManager.currentNotificationTonePosition
        ) { dialog, position ->
            if (PreferenceManager.currentNotificationTonePosition != position) {
                // handle language selection
                PreferenceManager.currentNotificationTonePosition = position
                PreferenceManager.currentNotificationTone = settingRepository.notificationTones[position]
                currentNotificationTone.value = PreferenceManager.currentNotificationTone

            }
            dialog.dismiss()
        }
        builder.create().show()
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
