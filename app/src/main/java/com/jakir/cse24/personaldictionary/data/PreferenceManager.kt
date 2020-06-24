package com.jakir.cse24.personaldictionary.data

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    /**
     * This method is to save user id
     * Created by Md. Jakir Hossain on 30/04/2019.
     * @param userId
     */
    var userId: String
        get() = prefs.getString("userId", "")!!
        set(userId) {
            editor.putString("userId", userId)
            editor.apply()
        }

    var isLoggedIn: Boolean
        get() = prefs.getBoolean("isLoggedIn", false)
        set(isLoggedIn) {
            editor.putBoolean("isLoggedIn", isLoggedIn)
            editor.apply()
        }

    var isNotificationActive: Boolean
        get() = prefs.getBoolean("notification", false)
        set(status) {
            editor.putBoolean("notification", status)
            editor.apply()
        }
    var isVibrationActive: Boolean
        get() = prefs.getBoolean("vibration", false)
        set(status) {
            editor.putBoolean("vibration", status)
            editor.apply()
        }

    var currentTheme: String
        get() = prefs.getString("theme", "Dark")!!
        set(theme) {
            editor.putString("theme", theme)
            editor.apply()
        }

    var currentThemePosition: Int
        get() = prefs.getInt("themePos", 0)
        set(themePos) {
            editor.putInt("themePos", themePos)
            editor.apply()
        }

    var currentLanguage: String
        get() = prefs.getString("language", "English")!!
        set(language) {
            editor.putString("language", language)
            editor.apply()
        }

    var currentLanguageCode: String
        get() = prefs.getString("languageCode", "en")!!
        set(language) {
            editor.putString("languageCode", language)
            editor.apply()
        }

    var currentLanguagePosition: Int
        get() = prefs.getInt("languagePos", 1)
        set(language) {
            editor.putInt("languagePos", language)
            editor.apply()
        }

    var currentNotificationTone: String
        get() = prefs.getString("notificationTone", "Default")!!
        set(notificationTone) {
            editor.putString("notificationTone", notificationTone)
            editor.apply()
        }
    var currentNotificationTonePosition: Int
        get() = prefs.getInt("notificationTonePos", 0)
        set(notificationTonePos) {
            editor.putInt("notificationTonePos", notificationTonePos)
            editor.apply()
        }

    fun init(context: Context) {
        prefs = context.getSharedPreferences("PD", Context.MODE_PRIVATE)
        editor = prefs.edit()
    }
}