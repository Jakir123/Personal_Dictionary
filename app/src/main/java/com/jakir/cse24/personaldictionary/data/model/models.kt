package com.jakir.cse24.personaldictionary.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vocabulary(
    val userId: String,
    val word: String,
    val type: String,
    val translation: Translation
) : Parcelable

@Parcelize
data class Translation(val meaning: String, val description: String, val example: String) :
    Parcelable

data class User(val name: String, val email: String, val phone: String)

data class ResponseModel(val status: Boolean, val message: String)