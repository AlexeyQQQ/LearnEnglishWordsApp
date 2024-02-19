package com.example.learnenglishwordsapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.entity.Statistics
import com.example.learnenglishwordsapp.domain.entity.Word

interface LearnEnglishRepository {

    fun getWord(original: String): LiveData<Word>

    fun getAllWords(): LiveData<List<Word>>

    fun addWord(word: Word)

    fun deleteWord(original: String)

    fun resetProgress()


    fun getListOfQuestions(): LiveData<List<Question>>

    fun checkAnswer(userAnswer: Int): Boolean

    fun showStatistics(): Statistics
}