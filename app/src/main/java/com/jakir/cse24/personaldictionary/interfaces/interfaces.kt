package com.jakir.cse24.personaldictionary.interfaces

import com.jakir.cse24.personaldictionary.data.model.LoginModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

interface ItemClickListener {
    fun onItemClick(vocabulary: Vocabulary)
}

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginModel: LoginModel)
    fun onFailure(loginModel: LoginModel)
}