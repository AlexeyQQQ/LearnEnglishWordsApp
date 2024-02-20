package com.example.learnenglishwordsapp.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.learnenglishwordsapp.data.database.AppDatabase
import com.example.learnenglishwordsapp.data.mappers.WordMapper
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.entity.Statistics
import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.repository.LearnEnglishRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LearnEnglishRepositoryImpl(
    application: Application,
) : LearnEnglishRepository {

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

    override suspend fun getQuestions(): Set<Question> {
        Log.d("TEST", "getQuestions")
        val questions = mutableSetOf<Question>()
        repeat(10) {
            getNextQuestion()?.let { nextQuestion ->
                questions.add(nextQuestion)
            }
        }
        return questions
    }

    override suspend fun checkAnswer(userAnswer: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun showStatistics(): Statistics {
        val dictionary = getAllWords()

        val wordsLearned = dictionary.filter { it.correctAnswersCount >= ANSWER_TO_STUDY }.size
        val wordsTotal = dictionary.size
        val percentageRatio = (wordsLearned.toDouble() / wordsTotal * 100).toInt()
        return Statistics(wordsLearned, wordsTotal, percentageRatio)
    }


    private suspend fun getNextQuestion(): Question? {
        Log.d("TEST", "getNextQuestion")
        val dictionary = getAllWords()

        var listOfUnlearnedWords = dictionary.filter {
            it.correctAnswersCount < ANSWER_TO_STUDY
        }
        if (listOfUnlearnedWords.isEmpty()) {
            return null
        }

        if (listOfUnlearnedWords.size < COUNT_OF_QUESTION_WORDS) {
            val listOfLearnedWords = dictionary
                .filter { it.correctAnswersCount >= ANSWER_TO_STUDY }
                .shuffled()
                .take(COUNT_OF_QUESTION_WORDS - listOfUnlearnedWords.size)
            listOfUnlearnedWords = listOfUnlearnedWords + listOfLearnedWords
        }

        val shuffledListWords = listOfUnlearnedWords
            .shuffled()
            .take(COUNT_OF_QUESTION_WORDS)
        var correctAnswer: Word
        do {
            correctAnswer = shuffledListWords.random()
        } while (correctAnswer.correctAnswersCount >= ANSWER_TO_STUDY)

        return Question(shuffledListWords, correctAnswer)
    }

    companion object {
        private const val ANSWER_TO_STUDY = 3
        private const val COUNT_OF_QUESTION_WORDS = 4
    }
}