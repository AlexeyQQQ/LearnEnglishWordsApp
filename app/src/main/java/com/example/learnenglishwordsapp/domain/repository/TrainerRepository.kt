package com.example.learnenglishwordsapp.domain.repository

import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.entity.Statistics

interface TrainerRepository {

    suspend fun getNextQuestion(): Question?

    suspend fun checkAnswer(userAnswer: Int): Boolean

    suspend fun showStatistics(): Statistics
}