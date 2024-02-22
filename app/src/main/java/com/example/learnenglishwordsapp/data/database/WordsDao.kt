package com.example.learnenglishwordsapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordsDao {

    @Query("SELECT * FROM table_of_words WHERE original = :original")
    suspend fun getWord(original: String): WordDbModel

    @Query("SELECT * FROM table_of_words ORDER BY original")
    suspend fun getAllWords(): List<WordDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(wordDbModel: WordDbModel)

    @Query("DELETE FROM table_of_words WHERE original = :original")
    suspend fun deleteWord(original: String)

    @Query("DELETE FROM table_of_words")
    suspend fun deleteAllWords()
}