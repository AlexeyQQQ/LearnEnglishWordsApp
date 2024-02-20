package com.example.learnenglishwordsapp.domain.repository

import com.example.learnenglishwordsapp.domain.entity.Word

interface DictionaryRepository {

    suspend fun getWord(original: String): Word

    suspend fun getAllWords(): List<Word>

    suspend fun addWord(word: Word)

    suspend fun deleteWord(original: String)

    suspend fun resetProgress()
}