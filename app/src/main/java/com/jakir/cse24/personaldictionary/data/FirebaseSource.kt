package com.jakir.cse24.personaldictionary.data

import com.google.firebase.auth.FirebaseAuth

class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
}