package com.example.learnenglishwordsapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.learnenglishwordsapp.data.repository.TrainerRepositoryImpl
import com.example.learnenglishwordsapp.domain.entity.GameResult
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.entity.Statistics
import com.example.learnenglishwordsapp.domain.usecases.CheckAnswerUseCase
import com.example.learnenglishwordsapp.domain.usecases.GetNextQuestionUseCase
import com.example.learnenglishwordsapp.domain.usecases.ShowStatisticsUseCase
import kotlinx.coroutines.launch

class LearnViewModel(application: Application) : AndroidViewModel(application) {

    private val trainerRepository = TrainerRepositoryImpl(application)
    private val getNextQuestionUseCase = GetNextQuestionUseCase(trainerRepository)
    private val checkAnswerUseCase = CheckAnswerUseCase(trainerRepository)
    private val showStatisticsUseCase = ShowStatisticsUseCase(trainerRepository)

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _statistics = MutableLiveData<Statistics>()
    val statistics: LiveData<Statistics>
        get() = _statistics

    init {
        getNextQuestion()
    }

    fun getNextQuestion() {
        viewModelScope.launch {
            _question.value = getNextQuestionUseCase()
            _statistics.value = showStatisticsUseCase()
        }
    }

    fun checkUserAnswer(userAnswer: Int) {
        viewModelScope.launch {
            _gameResult.value = checkAnswerUseCase(userAnswer)
        }
    }
}