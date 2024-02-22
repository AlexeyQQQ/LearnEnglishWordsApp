package com.example.learnenglishwordsapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.learnenglishwordsapp.R
import com.example.learnenglishwordsapp.databinding.FragmentChangeWordBinding

class ChangeWordFragment : Fragment() {

    private val args by navArgs<ChangeWordFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProvider(this)[ChangeWordViewModel::class.java]
    }

    private var _binding: FragmentChangeWordBinding? = null
    private val binding: FragmentChangeWordBinding
        get() = _binding ?: throw RuntimeException("FragmentChangeWordBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChangeWordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchRightMode()
        observeViewModel()
    }

    private fun launchRightMode() {
        when (args.screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
            else -> throw RuntimeException("Screen mode is unknown")
        }
    }

    private fun launchEditMode() {
        with(binding) {
            args.word?.let { word ->
                etOriginal.setText(word.original)
                etTranslation.setText(word.translation)

                btnSave.setOnClickListener {
                    if (etOriginal.text.isEmpty() || etTranslation.text.isEmpty()) {
                        showToastWithError()
                    } else {
                        viewModel.changeWord(
                            word,
                            etOriginal.text.toString(),
                            etTranslation.text.toString()
                        )
                    }
                }

                btnDelete.setOnClickListener {
                    viewModel.deleteWord(word)
                }

                btnLearnAgain.setOnClickListener {
                    viewModel.resetCorrectAnswersCount(word)
                }
            } ?: throw RuntimeException("Word-argument is unknown")
        }
    }

    private fun launchAddMode() {
        with(binding) {
            btnSave.setOnClickListener {
                if (etOriginal.text.isEmpty() || etTranslation.text.isEmpty()) {
                    showToastWithError()
                } else {
                    viewModel.addWord(
                        etOriginal.text.toString(),
                        etTranslation.text.toString()
                    )
                }
            }

            btnDelete.isEnabled = false
            btnLearnAgain.isEnabled = false
        }
    }


    private fun showToastWithError() {
        Toast.makeText(
            context,
            getString(R.string.toast_field_error),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun observeViewModel() {
        viewModel.closeScreen.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val MODE_EDIT = "mode_edit"
        const val MODE_ADD = "mode_add"
    }
}