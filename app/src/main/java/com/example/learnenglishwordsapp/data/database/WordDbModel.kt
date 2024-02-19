package com.example.learnenglishwordsapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_of_words")
class WordDbModel(
    @PrimaryKey
    val original: String,
    val translation: String,
    var correctAnswersCount: Int = BASIC_VALUE,
) {

    companion object {
        private const val BASIC_VALUE = 0
    }
}