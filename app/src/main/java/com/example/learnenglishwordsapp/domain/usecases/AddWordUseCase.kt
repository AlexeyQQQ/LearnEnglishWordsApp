package com.example.learnenglishwordsapp.domain.usecases

import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.repository.LearnEnglishRepository

class AddWordUseCase(
    private val repository: LearnEnglishRepository,
) {

    suspend operator fun invoke(word: Word) = repository.addWord(word)
}