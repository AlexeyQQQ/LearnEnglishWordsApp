package com.example.learnenglishwordsapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.learnenglishwordsapp.data.repository.DictionaryRepositoryImpl
import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.usecases.GetAllWordsUseCase
import kotlinx.coroutines.launch

class DictionaryViewModel(application: Application) : AndroidViewModel(application) {

    private val dictionaryRepository = DictionaryRepositoryImpl(application)
    private val getAllWordsUseCase = GetAllWordsUseCase(dictionaryRepository)

    private val _listWords = MutableLiveData<List<Word>>()
    val listWords: LiveData<List<Word>>
        get() = _listWords

    fun loadDictionary() {
        viewModelScope.launch {
            _listWords.value = getAllWordsUseCase()
        }
    }
}