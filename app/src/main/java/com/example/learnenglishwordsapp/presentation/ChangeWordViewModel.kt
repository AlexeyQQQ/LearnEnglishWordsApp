package com.example.learnenglishwordsapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.learnenglishwordsapp.data.repository.DictionaryRepositoryImpl
import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.usecases.AddWordUseCase
import com.example.learnenglishwordsapp.domain.usecases.DeleteWordUseCase
import kotlinx.coroutines.launch

class ChangeWordViewModel(application: Application) : AndroidViewModel(application) {

    private val dictionaryRepository = DictionaryRepositoryImpl(application)
    private val addWordUseCase = AddWordUseCase(dictionaryRepository)
    private val deleteWordUseCase = DeleteWordUseCase(dictionaryRepository)

    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen

    fun addWord(original: String, translation: String) {
        viewModelScope.launch {
            val newWord = Word(
                parseString(original),
                parseString(translation)
            )
            addWordUseCase(newWord)
            finishWork()
        }
    }

    fun changeWord(oldWord: Word, original: String, translation: String) {
        viewModelScope.launch {
            deleteWordUseCase(oldWord.original)
            val newWord = Word(
                parseString(original),
                parseString(translation),
                oldWord.correctAnswersCount
            )
            addWordUseCase(newWord)
            finishWork()
        }
    }

    fun resetCorrectAnswersCount(word: Word) {
        viewModelScope.launch {
            val resetWord = word.copy(correctAnswersCount = 0)
            addWordUseCase(resetWord)
            finishWork()
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            deleteWordUseCase(word.original)
            finishWork()
        }
    }

    private fun parseString(string: String): String {
        return string.trim()
    }

    private fun finishWork() {
        _closeScreen.value = Unit
    }
}