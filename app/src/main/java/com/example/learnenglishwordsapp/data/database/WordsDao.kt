package com.example.learnenglishwordsapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordsDao {

    @Query("SELECT * FROM table_of_words WHERE original = :original")
    fun getWord(original: String): LiveData<WordDbModel>

    @Query("SELECT * FROM table_of_words")
    fun getAllWords(): LiveData<List<WordDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(wordDbModel: WordDbModel)

    @Query("DELETE FROM table_of_words WHERE original = :original")
    fun deleteWord(original: String)

    @Query("DELETE FROM table_of_words")
    fun deleteAllWords()
}