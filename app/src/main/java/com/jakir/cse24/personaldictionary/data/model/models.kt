package com.jakir.cse24.personaldictionary.data.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Vocabulary(
    var id: String,
    var userId: String,
    var word: String,
    var type: String,
    var translation: Translation,
    var synonyms: String,
    var antonyms: String,
    @ServerTimestamp
    val timeStamp: Date? = null,
    var favourite: Boolean = false
) : Parcelable {
    constructor(
        word: String,
        type: String,
        translation: Translation,
        synonyms: String,
        antonyms: String
    ) : this("", "", word, type, translation, synonyms, antonyms)

    constructor() : this("", "", "", "", Translation("", "", ""), "", "")
}

@Parcelize
data class Translation(var meaning: String, var description: String, var example: String) :
    Parcelable {
    constructor() : this("", "", "")
}

data class User(val name: String, val email: String, val phone: String) {
    constructor() : this("", "", "")
}

data class ResponseModel(val status: Boolean, val message: String)

@Parcelize
data class AppInfo(
    val version_code: Int,
    val version_name: String,
    val developer_name: String,
    val developer_email: String,
    val developer_image: String,
    val playstore_link: String
) : Parcelable