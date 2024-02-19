package com.example.learnenglishwordsapp.domain.entity

data class Question(
    private val variants: List<Word>,
    private val correctAnswer: Word,
)