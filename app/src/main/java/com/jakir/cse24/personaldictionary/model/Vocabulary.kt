package com.jakir.cse24.personaldictionary.model

data class Vocabulary(val word: String,val type: String, val translation:Translation)
data class Translation(val meaning: String,val description: String, val example:String)
data class User(val name: String,val phone: String, val password:String) {
    constructor(phone: String, password: String) : this("",phone,password)
}
data class LoginModel(val status: Boolean, val message: String)