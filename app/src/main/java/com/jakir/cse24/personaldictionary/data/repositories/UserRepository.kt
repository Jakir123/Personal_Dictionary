package com.jakir.cse24.personaldictionary.data.repositories

import androidx.lifecycle.MutableLiveData
import com.jakir.cse24.personaldictionary.base.BaseRepository
import com.jakir.cse24.personaldictionary.data.model.User

class UserRepository : BaseRepository() {

    fun getUserInfo(): MutableLiveData<User> {
        val response = MutableLiveData<User>()
        userCollection.document(userId).get().addOnSuccessListener {doc->
            response.value = doc.toObject(User::class.java)!!
        }

        return response
    }
}