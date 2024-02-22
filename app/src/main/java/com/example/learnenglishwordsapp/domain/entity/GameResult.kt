package com.example.learnenglishwordsapp.domain.entity

data class GameResult(
    val isRightAnswer: Boolean,
    val userAnswer: Int,
    val rightAnswer: Int,
)