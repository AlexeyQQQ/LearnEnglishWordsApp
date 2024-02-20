package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.repository.TrainerRepository

class GetNextQuestionUseCase(
    private val repository: TrainerRepository,
) {

    suspend operator fun invoke() = repository.getNextQuestion()
}