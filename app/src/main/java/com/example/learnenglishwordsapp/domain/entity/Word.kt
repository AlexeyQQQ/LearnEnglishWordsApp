package com.example.learnenglishwordsapp.domain.entity

data class Word(
    private val original: String,
    private val translation: String,
    private var correctAnswersCount: Int,
)