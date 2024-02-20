package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.repository.DictionaryRepository

class DeleteWordUseCase(
    private val repository: DictionaryRepository,
) {

    suspend operator fun invoke(original: String) = repository.deleteWord(original)
}