package com.example.learnenglishwordsapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.learnenglishwordsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.listWords.observe(this) {
            Log.d("TEST", "ALL_WORDS_" + it.toString())
        }

        viewModel.statistics.observe(this) {
            Log.d("TEST", "STATISTICS_" + it.toString())
        }

        viewModel.questions.observe(this) {
            Log.d("TEST", "QUESTIONS_" + it.toString())
            with(binding) {
                tvOriginal.text = it.correctAnswer.original
                tvOption1.text = it.variants[0].translation
                tvOption2.text = it.variants[1].translation
                tvOption3.text = it.variants[2].translation
                tvOption4.text = it.variants[3].translation
            }
        }

        binding.tvOption1.setOnClickListener {
            viewModel.checkAnswer(1)
        }

        binding.tvOption2.setOnClickListener {
            viewModel.checkAnswer(2)
        }

        binding.tvOption3.setOnClickListener {
            viewModel.checkAnswer(3)
        }

        binding.tvOption4.setOnClickListener {
            viewModel.checkAnswer(4)
        }

        viewModel.isRightAnswer.observe(this) {
            if (it) {
                with(binding.tvAnswer) {
                    text = "Правильно"

                    val color = android.R.color.holo_green_dark
                    setTextColor(ContextCompat.getColor(this@MainActivity, color))

                    visibility = View.VISIBLE
                }
            } else {
                with(binding.tvAnswer) {
                    text = "Вы ошиблись"

                    val color = android.R.color.holo_red_dark
                    setTextColor(ContextCompat.getColor(this@MainActivity, color))

                    visibility = View.VISIBLE
                }
            }
        }
    }
}