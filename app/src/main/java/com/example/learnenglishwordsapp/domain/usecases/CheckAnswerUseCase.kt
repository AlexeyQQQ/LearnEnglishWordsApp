package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.repository.DictionaryRepository
import com.example.learnenglishwordsapp.domain.repository.TrainerRepository

class CheckAnswerUseCase(
    private val repository: TrainerRepository,
) {

    suspend operator fun invoke(userAnswer: Int) = repository.checkAnswer(userAnswer)
}