package com.example.learnenglishwordsapp.domain.entity

data class Word(
    private val original: String,
    private val translation: String,
    private var correctAnswersCount: Int = BASIC_VALUE,
) {

    companion object {
        private const val BASIC_VALUE = 0
    }
}