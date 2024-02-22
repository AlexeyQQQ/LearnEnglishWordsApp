package com.example.learnenglishwordsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.learnenglishwordsapp.databinding.ItemWordDictionaryBinding
import com.example.learnenglishwordsapp.domain.entity.Word

class DictionaryAdapter : ListAdapter<Word, DictionaryViewHolder>(DictionaryDiffCallback) {

    var onShopItemClickListener: ((Word) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val binding = ItemWordDictionaryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DictionaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        val word = getItem(position)
        with(holder) {
            binding.tvOriginal.text = word.original
            binding.tvTranslation.text = word.translation
            binding.tvCorrectAnswersCount.text = word.correctAnswersCount.toString()

            itemView.setOnClickListener {
                onShopItemClickListener?.invoke(word)
            }
        }
    }

}