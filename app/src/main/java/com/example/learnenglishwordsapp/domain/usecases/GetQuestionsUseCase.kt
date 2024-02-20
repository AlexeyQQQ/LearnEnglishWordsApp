package com.example.learnenglishwordsapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.repository.LearnEnglishRepository

class GetQuestionsUseCase(
    private val repository: LearnEnglishRepository
) {

    suspend operator fun invoke() = repository.getQuestions()
}