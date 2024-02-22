package com.example.learnenglishwordsapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.learnenglishwordsapp.domain.entity.Word

object DictionaryDiffCallback : DiffUtil.ItemCallback<Word>() {

    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.original == newItem.original
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}