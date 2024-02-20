package com.example.learnenglishwordsapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

        viewModel.questions.observe(this) {
            Log.d("TEST", "QUESTIONS_" + it.size + it.toString())
        }

        viewModel.statistics.observe(this) {
            Log.d("TEST", "STATISTICS_" + it.toString())
        }
    }
}