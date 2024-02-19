package com.example.learnenglishwordsapp.domain.entity

data class Statistics(
    private val wordsLearned: Int,
    private val wordsTotal: Int,
    private val percentageRatio: Int,
)