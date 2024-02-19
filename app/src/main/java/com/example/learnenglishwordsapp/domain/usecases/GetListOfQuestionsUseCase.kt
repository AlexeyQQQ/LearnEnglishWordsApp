package com.example.learnenglishwordsapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.repository.LearnEnglishRepository

class GetListOfQuestionsUseCase(
    private val repository: LearnEnglishRepository
) {

    operator fun invoke(): LiveData<List<Question>> = repository.getListOfQuestions()
}