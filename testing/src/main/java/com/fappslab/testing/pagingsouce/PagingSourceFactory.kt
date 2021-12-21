package com.fappslab.testing.pagingsouce

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fappslab.core.domain.model.Character

class PagingSourceFactory {

    @Suppress("MagicNumber")
    fun create(heroes: List<Character>) = object : PagingSource<Int, Character>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
            return LoadResult.Page(
                data = heroes,
                null,
                20
            )
        }

        override fun getRefreshKey(state: PagingState<Int, Character>) = 1
    }
}
