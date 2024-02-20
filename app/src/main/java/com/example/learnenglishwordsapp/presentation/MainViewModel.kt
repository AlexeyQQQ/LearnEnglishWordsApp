package com.example.learnenglishwordsapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.learnenglishwordsapp.data.repository.LearnEnglishRepositoryImpl
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.entity.Statistics
import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.usecases.GetAllWordsUseCase
import com.example.learnenglishwordsapp.domain.usecases.GetQuestionsUseCase
import com.example.learnenglishwordsapp.domain.usecases.ShowStatisticsUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LearnEnglishRepositoryImpl(application)
    private val getAllWordsUseCase = GetAllWordsUseCase(repository)
    private val getQuestionsUseCase = GetQuestionsUseCase(repository)
    private val showStatisticsUseCase = ShowStatisticsUseCase(repository)

    val listWords = MutableLiveData<List<Word>>()

    val questions = MutableLiveData<Set<Question>>()

    val statistics = MutableLiveData<Statistics>()

    init {
        viewModelScope.launch {
            listWords.postValue(getAllWordsUseCase())
            questions.postValue(getQuestionsUseCase())
            statistics.postValue(showStatisticsUseCase())
        }
    }
}