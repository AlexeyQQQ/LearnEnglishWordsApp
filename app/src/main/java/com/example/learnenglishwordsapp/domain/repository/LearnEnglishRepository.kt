package com.example.learnenglishwordsapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.entity.Statistics

interface LearnEnglishRepository {

    fun getListOfQuestions(): LiveData<List<Question>>

    fun checkAnswer(userAnswer: Int): Boolean

    fun showStatistics(): Statistics

    fun resetProgress()
}