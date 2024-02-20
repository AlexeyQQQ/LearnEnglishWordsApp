package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.repository.DictionaryRepository

class ResetProgressUseCase(
    private val repository: DictionaryRepository,
) {

    suspend operator fun invoke() = repository.resetProgress()
}