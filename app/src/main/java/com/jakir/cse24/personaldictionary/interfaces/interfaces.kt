package com.jakir.cse24.personaldictionary.interfaces

import android.view.View
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

interface ItemClickListener {
    fun onItemClick(vocabulary: Vocabulary)
}

interface LogoutListener{
    fun onLogoutPressed()
}

interface AuthListener {
    fun onStarted()
    fun onSuccess(responseModel: ResponseModel)
    fun onFailure(responseModel: ResponseModel)
}