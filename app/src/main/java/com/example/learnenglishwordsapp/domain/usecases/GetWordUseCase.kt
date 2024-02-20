package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.repository.LearnEnglishRepository

class GetWordUseCase(
    private val repository: LearnEnglishRepository,
) {

    suspend operator fun invoke(original: String) = repository.getWord(original)
}