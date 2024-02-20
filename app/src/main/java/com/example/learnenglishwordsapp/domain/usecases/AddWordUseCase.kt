package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.repository.DictionaryRepository

class AddWordUseCase(
    private val repository: DictionaryRepository,
) {

    suspend operator fun invoke(word: Word) = repository.addWord(word)
}