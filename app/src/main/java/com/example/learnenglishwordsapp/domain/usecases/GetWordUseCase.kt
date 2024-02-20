package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.repository.DictionaryRepository

class GetWordUseCase(
    private val repository: DictionaryRepository,
) {

    suspend operator fun invoke(original: String) = repository.getWord(original)
}