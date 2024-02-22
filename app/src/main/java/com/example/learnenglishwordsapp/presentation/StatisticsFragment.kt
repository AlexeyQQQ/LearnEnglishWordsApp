package com.example.learnenglishwordsapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.learnenglishwordsapp.R
import com.example.learnenglishwordsapp.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding: FragmentStatisticsBinding
        get() = _binding ?: throw RuntimeException("FragmentDictionaryBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[StatisticsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.statistics.observe(viewLifecycleOwner) { statistics ->
            Log.d("TEST", statistics.toString())
            with(binding) {
                tvTotalWords.text = String.format(
                    getString(R.string.tv_total_words_to_study),
                    statistics.wordsTotal.toString()
                )
                tvLearnedWords.text = String.format(
                    getString(R.string.tv_words_learned),
                    statistics.wordsLearned.toString()
                )
                tvProgress.text = String.format(
                    getString(R.string.tv_progress),
                    statistics.percentageRatio.toString()
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}