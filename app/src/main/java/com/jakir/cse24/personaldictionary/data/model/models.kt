package com.jakir.cse24.personaldictionary.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vocabulary(
    var userId: String,
    val word: String,
    val type: String,
    val translation: Translation
) : Parcelable {
    constructor(word:String,type: String,translation: Translation) : this("",word, type, translation)
    constructor() : this("","", "", Translation("", "", ""))
}

@Parcelize
data class Translation(val meaning: String, val description: String, val example: String) :
    Parcelable {
    constructor() : this("", "", "")
}

data class User(val name: String, val email: String, val phone: String) {
}

data class ResponseModel(val status: Boolean, val message: String)