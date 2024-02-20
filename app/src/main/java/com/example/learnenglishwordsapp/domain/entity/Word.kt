package com.example.learnenglishwordsapp.domain.entity

data class Word(
    val original: String,
    val translation: String,
    var correctAnswersCount: Int = BASIC_VALUE,
) {

    companion object {
        private const val BASIC_VALUE = 0
    }
}