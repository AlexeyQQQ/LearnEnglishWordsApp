package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.repository.LearnEnglishRepository

class CheckAnswerUseCase(
    private val repository: LearnEnglishRepository,
) {

    operator fun invoke(userAnswer: Int) = repository.checkAnswer(userAnswer)
}