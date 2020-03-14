package com.jakir.cse24.personaldictionary.base

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jakir.cse24.personaldictionary.data.FirebaseSource

open class BaseRepository {
    protected val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    protected val userId: String by lazy {
        FirebaseSource.firebaseAuth.currentUser!!.uid
    }

    protected val vocabularyCollection: CollectionReference by lazy {
        db.collection("vocabularies")
    }
    protected val userCollection: CollectionReference by lazy {
        db.collection("users")
    }


}