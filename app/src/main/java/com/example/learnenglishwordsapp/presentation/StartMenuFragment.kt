package com.example.learnenglishwordsapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.learnenglishwordsapp.R
import com.example.learnenglishwordsapp.databinding.FragmentStartMenuBinding


class StartMenuFragment : Fragment() {

    private var _binding: FragmentStartMenuBinding? = null
    private val binding: FragmentStartMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentStartMenuBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStartMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDictionary.setOnClickListener {
            findNavController().navigate(R.id.action_startMenuFragment_to_dictionaryFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}