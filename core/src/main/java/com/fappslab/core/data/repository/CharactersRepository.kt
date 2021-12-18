package com.fappslab.core.data.repository

import androidx.paging.PagingSource
import com.fappslab.core.domain.model.Character

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>
}