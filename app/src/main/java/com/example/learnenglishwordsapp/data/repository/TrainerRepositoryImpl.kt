package com.example.learnenglishwordsapp.data.repository

import android.app.Application
import com.example.learnenglishwordsapp.domain.entity.Question
import com.example.learnenglishwordsapp.domain.entity.Statistics
import com.example.learnenglishwordsapp.domain.entity.Word
import com.example.learnenglishwordsapp.domain.repository.TrainerRepository
import com.example.learnenglishwordsapp.domain.usecases.AddWordUseCase
import com.example.learnenglishwordsapp.domain.usecases.GetAllWordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainerRepositoryImpl(application: Application) : TrainerRepository {

    private val dictionaryRepository = DictionaryRepositoryImpl(application)
    private val getAllWordsUseCase = GetAllWordsUseCase(dictionaryRepository)
    private val addWordUseCase = AddWordUseCase(dictionaryRepository)

    private var question: Question? = null
    private var dictionary: List<Word>? = null

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            dictionary = getAllWordsUseCase()
        }
    }

    override suspend fun showStatistics(): Statistics {
        dictionary?.let {
            val wordsLearned = it.filter { it.correctAnswersCount >= ANSWER_TO_STUDY }.size
            val wordsTotal = it.size
            val percentageRatio = (wordsLearned.toDouble() / wordsTotal * 100).toInt()
            return Statistics(wordsLearned, wordsTotal, percentageRatio)
        }
        return Statistics(0, 0, 0)
    }


    override suspend fun getNextQuestion(): Question? {
        dictionary?.let {
            var listOfUnlearnedWords = it.filter {
                it.correctAnswersCount < ANSWER_TO_STUDY
            }
            if (listOfUnlearnedWords.isEmpty()) {
                return null
            }

            if (listOfUnlearnedWords.size < COUNT_OF_QUESTION_WORDS) {
                val listOfLearnedWords = it
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

            question = Question(shuffledListWords, correctAnswer)
            return question
        }
        return null
    }

    override suspend fun checkAnswer(userAnswer: Int): Boolean {
        return question?.let {
            val studiedWord = it.correctAnswer
            if (userAnswer == it.variants.indexOf(studiedWord) + 1) {
                studiedWord.correctAnswersCount++
                addWordUseCase(studiedWord)
                true
            } else {
                false
            }
        } ?: false
    }

//    private suspend fun getNextQuestion(): Question? {
//        Log.d("TEST", "getNextQuestion")
//        val dictionary = getAllWordsUseCase()
//
//        var listOfUnlearnedWords = dictionary.filter {
//            it.correctAnswersCount < ANSWER_TO_STUDY
//        }
//        if (listOfUnlearnedWords.isEmpty()) {
//            return null
//        }
//
//        if (listOfUnlearnedWords.size < COUNT_OF_QUESTION_WORDS) {
//            val listOfLearnedWords = dictionary
//                .filter { it.correctAnswersCount >= ANSWER_TO_STUDY }
//                .shuffled()
//                .take(COUNT_OF_QUESTION_WORDS - listOfUnlearnedWords.size)
//            listOfUnlearnedWords = listOfUnlearnedWords + listOfLearnedWords
//        }
//
//        val shuffledListWords = listOfUnlearnedWords
//            .shuffled()
//            .take(COUNT_OF_QUESTION_WORDS)
//        var correctAnswer: Word
//        do {
//            correctAnswer = shuffledListWords.random()
//        } while (correctAnswer.correctAnswersCount >= ANSWER_TO_STUDY)
//
//        return Question(shuffledListWords, correctAnswer)
//    }

    companion object {
        private const val ANSWER_TO_STUDY = 3
        private const val COUNT_OF_QUESTION_WORDS = 4
    }
}