package com.jakir.cse24.personaldictionary.data

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager{
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
    fun init(context: Context) {
        prefs = context.getSharedPreferences("PD", Context.MODE_PRIVATE)
        editor = prefs.edit()
    }
}