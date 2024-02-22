package com.example.learnenglishwordsapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(
    val original: String,
    val translation: String,
    var correctAnswersCount: Int = BASIC_VALUE,
) : Parcelable {

    companion object {
        private const val BASIC_VALUE = 0
    }
}