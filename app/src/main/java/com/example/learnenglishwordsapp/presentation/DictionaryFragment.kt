package com.example.learnenglishwordsapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnenglishwordsapp.databinding.FragmentDictionaryBinding
import com.example.learnenglishwordsapp.presentation.adapters.DictionaryAdapter

class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding: FragmentDictionaryBinding
        get() = _binding ?: throw RuntimeException("FragmentDictionaryBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[DictionaryViewModel::class.java]
    }

    private val dictionaryAdapter by lazy {
        DictionaryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvWordsList.adapter = dictionaryAdapter
        binding.rvWordsList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        observeViewModel()
        setupAdapterClickListeners()
        setupClickListeners()

        viewModel.loadDictionary()
    }

    private fun observeViewModel() {
        viewModel.listWords.observe(viewLifecycleOwner) {
            dictionaryAdapter.submitList(it)
            Log.d("TEST", "DICTIONARY_$it")
        }
    }

    private fun setupClickListeners() {
        binding.flbtnAddWord.setOnClickListener {
            findNavController().navigate(
                DictionaryFragmentDirections.actionDictionaryFragmentToChangeWordFragment(
                    null,
                    ChangeWordFragment.MODE_ADD
                )
            )
        }
    }

    private fun setupAdapterClickListeners() {
        dictionaryAdapter.onShopItemClickListener = {
            findNavController().navigate(
                DictionaryFragmentDirections.actionDictionaryFragmentToChangeWordFragment(
                    it,
                    ChangeWordFragment.MODE_EDIT
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}