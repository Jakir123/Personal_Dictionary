package com.jakir.cse24.personaldictionary.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vocabulary(val word: String,val type: String, val translation:Translation): Parcelable

@Parcelize
data class Translation(val meaning: String,val description: String, val example:String): Parcelable

data class User(val name: String,val phone: String, val password:String) {
    constructor(phone: String, password: String) : this("",phone,password)
}
data class LoginModel(val status: Boolean, val message: String)