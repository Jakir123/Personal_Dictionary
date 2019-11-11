package com.jakir.cse24.personaldictionary.interfaces

import com.jakir.cse24.personaldictionary.model.Vocabulary

interface ItemClickListener{
    fun onItemClick(vocabulary: Vocabulary)
}