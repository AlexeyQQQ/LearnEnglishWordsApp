package com.example.learnenglishwordsapp.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.learnenglishwordsapp.R
import com.example.learnenglishwordsapp.databinding.FragmentLearnBinding

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null
    private val binding: FragmentLearnBinding
        get() = _binding ?: throw RuntimeException("FragmentDictionaryBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[LearnViewModel::class.java]
    }

    private val listOfOptions by lazy {
        listOf(
            binding.tvOption1,
            binding.tvOption2,
            binding.tvOption3,
            binding.tvOption4
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupClickListeners()
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) { question ->
            if (question == null) {
                Toast.makeText(
                    context,
                    getString(R.string.toast_all_words_learned),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                for ((index, option) in listOfOptions.withIndex()) {
                    option.text = question.variants[index].translation
                }

                binding.tvOriginal.text = question.correctAnswer.original
            }

            val colorBlackId = android.R.color.black
            listOfOptions.forEach { option ->
                option.isClickable = true
                option.setTextColor(
                    ContextCompat.getColor(requireContext(), colorBlackId)
                )
            }

            binding.btnSkip.text = getString(R.string.btn_skip)
        }

        viewModel.gameResult.observe(viewLifecycleOwner) {
            listOfOptions.forEach { option ->
                option.isClickable = false
            }
            binding.btnSkip.text = getString(R.string.btn_next)

            val colorGreenId = android.R.color.holo_green_dark
            val colorRedId = android.R.color.holo_red_dark

            if (it.isRightAnswer) {
                listOfOptions[it.userAnswer].setTextColor(
                    ContextCompat.getColor(requireContext(), colorGreenId)
                )
            } else {
                listOfOptions[it.userAnswer].setTextColor(
                    ContextCompat.getColor(requireContext(), colorRedId)
                )
                listOfOptions[it.rightAnswer].setTextColor(
                    ContextCompat.getColor(requireContext(), colorGreenId)
                )
            }
        }

        viewModel.statistics.observe(viewLifecycleOwner) {
            with(binding.pbLearnedWords) {
                val colorGreenId = android.R.color.holo_green_dark
                max = it.wordsTotal
                setProgress(it.wordsLearned, true)
                progressTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        colorGreenId
                    )
                )
            }
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            btnSkip.setOnClickListener {
                viewModel.getNextQuestion()
            }

            for ((index, option) in listOfOptions.withIndex()) {
                option.setOnClickListener {
                    viewModel.checkUserAnswer(index)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}