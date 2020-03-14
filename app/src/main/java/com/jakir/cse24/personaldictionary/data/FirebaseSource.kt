package com.jakir.cse24.personaldictionary.data

import com.google.firebase.auth.FirebaseAuth

object FirebaseSource {

    public val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
}