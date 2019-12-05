package com.jakir.cse24.personaldictionary.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager private constructor(context: Context) {
    private val prefs: SharedPreferences
    private val editor: SharedPreferences.Editor

    /**
     * This method is to save user id
     * Created by Md. Jakir Hossain on 30/04/2019.
     * @param userId
     */
    var userId: String?
        get() = prefs.getString("userId", "")
        set(userId) {
            editor.putString("userId", userId)
            editor.apply()
        }



    var doctorId: Int
        get() = prefs.getInt("doctorId", 1)
        set(doctorId) {
            editor.putInt("doctorId", doctorId)
            editor.apply()
        }

    companion object {
        private var jInstance: PreferenceManager? = null
        /**
         * This method is for getting instance using singleton design pattern
         * Created by Md. Jakir Hossain on 30/04/2019.
         *
         * @param context application context
         * @return instance of PreferenceManager
         */
        @Synchronized
        fun getInstance(context: Context): PreferenceManager? {
            return if (jInstance != null) {
                jInstance
            } else {
                jInstance =
                    PreferenceManager(context)
                jInstance
            }
        }
    }

    init {
        prefs = context.getSharedPreferences("iHealthCare", Context.MODE_PRIVATE)
        editor = prefs.edit()
    }
}