package com.jakir.cse24.personaldictionary.data.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Vocabulary(
    var id: String,
    var userId: String,
    val word: String,
    val type: String,
    val translation: Translation,
    val synonyms: String,
    val antonyms: String,
    @ServerTimestamp
    val timeStamp: Date? = null
) : Parcelable {
    constructor(word:String,type: String,translation: Translation) : this("","",word, type, translation,"","")
    constructor() : this("","","", "", Translation("", "", ""),"","")
}

@Parcelize
data class Translation(val meaning: String, val description: String, val example: String) :
    Parcelable {
    constructor() : this("", "", "")
}

data class User(val name: String, val email: String, val phone: String) {
}

data class ResponseModel(val status: Boolean, val message: String)