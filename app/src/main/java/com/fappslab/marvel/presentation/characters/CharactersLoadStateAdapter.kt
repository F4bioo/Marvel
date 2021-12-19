package com.fappslab.marvel.presentation.characters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class CharactersLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CharactersLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = CharactersLoadStateViewHolder.create(parent, retry)

    override fun onBindViewHolder(
        holder: CharactersLoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}
