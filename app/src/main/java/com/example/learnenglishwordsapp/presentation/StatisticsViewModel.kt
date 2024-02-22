package com.example.learnenglishwordsapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.learnenglishwordsapp.data.repository.TrainerRepositoryImpl
import com.example.learnenglishwordsapp.domain.entity.Statistics
import com.example.learnenglishwordsapp.domain.usecases.ShowStatisticsUseCase
import kotlinx.coroutines.launch

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {

    private val trainerRepository = TrainerRepositoryImpl(application)
    private val showStatisticsUseCase = ShowStatisticsUseCase(trainerRepository)

    private val _statistics = MutableLiveData<Statistics>()
    val statistics: LiveData<Statistics>
        get() = _statistics

    init {
        viewModelScope.launch {
            _statistics.value = showStatisticsUseCase()
        }
    }
}