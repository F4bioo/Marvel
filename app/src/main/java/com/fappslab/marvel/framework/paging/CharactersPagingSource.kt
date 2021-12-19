package com.fappslab.marvel.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fappslab.core.data.repository.CharactersRemoteDataSource
import com.fappslab.core.domain.model.Character
import com.fappslab.marvel.framework.network.response.DataWrapperResponse
import com.fappslab.marvel.framework.network.response.toCharacterModel

class CharactersPagingSource(
    private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>,
    private val query: String
) : PagingSource<Int, Character>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val offset = params.key ?: 0

            val queries = hashMapOf("offset" to offset.toString())

            if (query.isNotEmpty()) queries["namesStartWith"] = query

            val response = remoteDataSource.fetchCharacters(queries)

            val responseOffset = response.data.offset
            val totalCharacters = response.data.total

            LoadResult.Page(
                data = response.data.results.map { it.toCharacterModel() },
                prevKey = null,
                nextKey = if (responseOffset < totalCharacters) {
                    responseOffset + LIMIT
                } else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        const val LIMIT = 20
    }

}
