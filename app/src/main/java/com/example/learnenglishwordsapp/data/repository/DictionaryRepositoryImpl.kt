package com.example.learnenglishwordsapp.data.repository

import android.app.Application
import com.example.learnenglishwordsapp.data.database.AppDatabase
import com.example.learnenglishwordsapp.data.mappers.WordMapper
import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.repository.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DictionaryRepositoryImpl(
    application: Application,
) : DictionaryRepository {

    private val wordsDao = AppDatabase.getInstance(application).WordsDao()
    private val mapper = WordMapper()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            val listOfWords = listOf(
                Word("hello", "привет", 3),
                Word("dog", "собака", 3),
                Word("cat", "кошка", 3),
                Word("thank you", "спасибо", 3),
                Word("text", "текст", 3),
                Word("news", "новость", 0),
                Word("word", "слово", 0),
                Word("letter", "письмо", 0),
                Word("message", "сообщение", 0),
                Word("note", "заметка", 0),
            )
            listOfWords.forEach {
                addWord(it)
            }
        }
    }

    override suspend fun getWord(original: String): Word {
        return mapper.mapDbModelToEntity(wordsDao.getWord(original))
    }


    override suspend fun getAllWords(): List<Word> {
        return mapper.mapListDbModelToListEntity(wordsDao.getAllWords())
    }

    override suspend fun addWord(word: Word) {
        wordsDao.addWord(mapper.mapEntityToDbModel(word))
    }

    override suspend fun deleteWord(original: String) {
        wordsDao.deleteWord(original)
    }

    override suspend fun resetProgress() {
        wordsDao.deleteAllWords()
    }
}