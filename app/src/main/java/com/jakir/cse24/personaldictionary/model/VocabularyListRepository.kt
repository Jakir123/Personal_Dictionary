package com.jakir.cse24.personaldictionary.model

class VocabularyListRepository {
    private var nextItem = 1

    fun getVocabularies(pageSize: Int = 20): List<Vocabulary> {

        val items = mutableListOf<Vocabulary>()
//        val lastItem = nextItem + pageSize - 1
//
//        for (i in nextItem..lastItem) {
//            items.add("Item $i")
//        }

//        nextItem = lastItem + 1
        items.add(Vocabulary("Vulnerability","Noun",Translation("Durbolota","who is weak","he is vulnerable then him!")))
        items.add(Vocabulary("Despite","Noun",Translation("Sotteo","Despite","Despite i am sick i want to play!")))

        return items
    }
}