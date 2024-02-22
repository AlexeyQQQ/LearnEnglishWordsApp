package com.example.learnenglishwordsapp.data.repository

import android.app.Application
import android.util.Log
import com.example.learnenglishwordsapp.domain.entity.GameResult
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.entity.Statistics
import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.repository.TrainerRepository
import com.example.learnenglishwordsapp.domain.usecases.AddWordUseCase
import com.example.learnenglishwordsapp.domain.usecases.GetAllWordsUseCase

class TrainerRepositoryImpl(application: Application) : TrainerRepository {

    private val dictionaryRepository = DictionaryRepositoryImpl(application)
    private val getAllWordsUseCase = GetAllWordsUseCase(dictionaryRepository)
    private val addWordUseCase = AddWordUseCase(dictionaryRepository)

    private var question: Question? = null

    override suspend fun showStatistics(): Statistics {
        val dictionary = getAllWordsUseCase()
        if (dictionary.isEmpty()) {
            return Statistics(0, 0, 0)
        }

        val wordsLearned = dictionary.filter { it.correctAnswersCount >= ANSWER_TO_STUDY }.size
        val wordsTotal = dictionary.size
        val percentageRatio = (wordsLearned.toDouble() / wordsTotal * 100).toInt()
        return Statistics(wordsLearned, wordsTotal, percentageRatio)
    }


    override suspend fun getNextQuestion(): Question? {
        val dictionary = getAllWordsUseCase()
        Log.d("TEST", "DICTIONARY_" + dictionary.size + dictionary.toString())

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

        Log.d("TEST", "UNLEARNED_" + listOfUnlearnedWords.size + listOfUnlearnedWords.toString())

        val shuffledListWords = listOfUnlearnedWords
            .shuffled()
            .take(COUNT_OF_QUESTION_WORDS)
        var correctAnswer: Word
        do {
            correctAnswer = shuffledListWords.random()
        } while (correctAnswer.correctAnswersCount >= ANSWER_TO_STUDY)

        question = Question(shuffledListWords, correctAnswer)
        return question
    }

    override suspend fun checkAnswer(userAnswer: Int): GameResult {
        return question?.let {
            val studiedWord = it.correctAnswer
            if (userAnswer == it.variants.indexOf(studiedWord)) {
                studiedWord.correctAnswersCount++
                addWordUseCase(studiedWord)
                GameResult(true, userAnswer, it.variants.indexOf(studiedWord))
            } else {
                GameResult(false, userAnswer, it.variants.indexOf(studiedWord))
            }
        } ?: throw RuntimeException("Question == null")
    }

    companion object {
        private const val ANSWER_TO_STUDY = 3
        private const val COUNT_OF_QUESTION_WORDS = 4
    }
}