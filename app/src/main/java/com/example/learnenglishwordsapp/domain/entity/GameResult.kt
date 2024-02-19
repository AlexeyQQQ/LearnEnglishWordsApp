package com.example.learnenglishwordsapp.domain.entity

data class GameResult(
    private val countOfQuestions: Int,
    private val countOfRightAnswers: Int,
    private val countOfWordsStudied: Int,
    private val countOfWordsToRepeat: Int,
)