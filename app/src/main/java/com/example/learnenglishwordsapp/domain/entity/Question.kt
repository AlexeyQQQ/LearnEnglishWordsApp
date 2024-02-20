package com.example.learnenglishwordsapp.domain.entity

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word,
)