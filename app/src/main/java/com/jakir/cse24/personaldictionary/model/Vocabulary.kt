package com.jakir.cse24.personaldictionary.model

data class Vocabulary(val word: String,val type: String, val translation:Translation)
data class Translation(val meaning: String,val description: String, val example:String)