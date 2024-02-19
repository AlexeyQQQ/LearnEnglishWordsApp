package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.repository.LearnEnglishRepository

class ShowStatisticsUseCase(
    private val repository: LearnEnglishRepository
) {

    operator fun invoke() = repository.showStatistics()
}