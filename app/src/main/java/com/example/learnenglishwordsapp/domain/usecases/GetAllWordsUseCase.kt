package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.repository.LearnEnglishRepository

class GetAllWordsUseCase(
    private val repository: LearnEnglishRepository,
) {

    suspend operator fun invoke() = repository.getAllWords()
}