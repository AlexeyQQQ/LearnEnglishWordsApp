package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.repository.LearnEnglishRepository

class DeleteWordUseCase(
    private val repository: LearnEnglishRepository,
) {

    suspend operator fun invoke(original: String) = repository.deleteWord(original)
}