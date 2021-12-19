package com.fappslab.marvel.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.fappslab.marvel.databinding.ItemCharacterLoadStateBinding

class CharactersLoadStateViewHolder(
    itemBinding: ItemCharacterLoadStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val binding = ItemCharacterLoadStateBinding.bind(itemView)
    private val progressLoadingMore = binding.progressLoadingMore
    private val textTryAgain = binding.textTryAgain.also { it.setOnClickListener { retry() } }

    fun bind(loadState: LoadState) {
        progressLoadingMore.isVisible = loadState is LoadState.Loading
        textTryAgain.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharactersLoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemCharacterLoadStateBinding.inflate(inflater, parent, false)

            return CharactersLoadStateViewHolder(itemBinding, retry)
        }
    }
}
