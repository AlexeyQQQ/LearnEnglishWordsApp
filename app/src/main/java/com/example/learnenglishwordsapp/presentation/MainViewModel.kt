package com.example.learnenglishwordsapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.learnenglishwordsapp.data.repository.DictionaryRepositoryImpl
import com.example.learnenglishwordsapp.data.repository.TrainerRepositoryImpl
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.entity.Statistics
import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.usecases.CheckAnswerUseCase
import com.example.learnenglishwordsapp.domain.usecases.GetAllWordsUseCase
import com.example.learnenglishwordsapp.domain.usecases.GetNextQuestionUseCase
import com.example.learnenglishwordsapp.domain.usecases.ShowStatisticsUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dictionaryRepository = DictionaryRepositoryImpl(application)
    private val getAllWordsUseCase = GetAllWordsUseCase(dictionaryRepository)

    private val trainerRepository = TrainerRepositoryImpl(application)
    private val getNextQuestionUseCase = GetNextQuestionUseCase(trainerRepository)
    private val showStatisticsUseCase = ShowStatisticsUseCase(trainerRepository)
    private val checkAnswerUseCase = CheckAnswerUseCase(trainerRepository)

    val listWords = MutableLiveData<List<Word>>()

    val statistics = MutableLiveData<Statistics>()

    val questions = MutableLiveData<Question>()

    val isRightAnswer = MutableLiveData<Boolean>()

    fun checkAnswer(userAnswer: Int) {
        viewModelScope.launch {
            isRightAnswer.value = checkAnswerUseCase(userAnswer)
        }
    }

    init {
        viewModelScope.launch {
            listWords.value = getAllWordsUseCase()
            questions.value = getNextQuestionUseCase()
            statistics.value = showStatisticsUseCase()
        }
    }
}