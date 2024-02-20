package com.example.learnenglishwordsapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.entity.Statistics
import com.example.learnenglishwordsapp.domain.entity.Word

interface LearnEnglishRepository {

    suspend fun getWord(original: String): Word

    suspend fun getAllWords(): List<Word>

    suspend fun addWord(word: Word)

    suspend fun deleteWord(original: String)

    suspend fun resetProgress()


    suspend fun getQuestions(): Set<Question>

    suspend fun checkAnswer(userAnswer: Int): Boolean

    suspend fun showStatistics(): Statistics
}