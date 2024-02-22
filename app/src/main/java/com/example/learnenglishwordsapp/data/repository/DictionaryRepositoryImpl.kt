package com.example.learnenglishwordsapp.data.repository

import android.app.Application
import com.example.learnenglishwordsapp.data.database.AppDatabase
import com.example.learnenglishwordsapp.data.mappers.WordMapper
import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.repository.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class DictionaryRepositoryImpl(
    application: Application,
) : DictionaryRepository {

    private val wordsDao = AppDatabase.getInstance(application).WordsDao()
    private val mapper = WordMapper()

    private val scope = CoroutineScope(Dispatchers.IO)

//    init {
//        scope.launch {
//            val listOfWords = listOf(
//                Word("hello", "привет", 3),
//                Word("dog", "собака", 3),
//                Word("cat", "кошка", 3),
//                Word("thank you", "спасибо", 2),
//                Word("text", "текст", 2),
//                Word("news", "новость", 2),
//                Word("word", "слово", 1),
//                Word("letter", "письмо", 1),
//                Word("message", "сообщение", 1),
//                Word("note", "заметка", 0),
//                Word("weather", "погода", 0),
//                Word("sun", "солнце", 0),
//                Word("pigeon", "голубь", 0),
//                Word("human", "человек", 0),
//                Word("piano", "пианино", 0),
//                Word("fork", "вилка", 0),
//                Word("spoon", "ложка", 0),
//                Word("country", "страна", 0),
//                Word("apple", "яблоко", 0),
//                Word("mouse", "мышь", 0),
//            )
//            listOfWords.forEach {
//                addWord(it)
//            }
//        }
//    }

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