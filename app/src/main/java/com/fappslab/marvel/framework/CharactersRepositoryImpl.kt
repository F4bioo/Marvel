package com.fappslab.marvel.framework

import androidx.paging.PagingSource
import com.fappslab.core.data.repository.CharactersRemoteDataSource
import com.fappslab.core.data.repository.CharactersRepository
import com.fappslab.core.domain.model.Character
import com.fappslab.marvel.framework.network.response.DataWrapperResponse
import javax.inject.Inject

class CharactersRepositoryImpl
@Inject
constructor(
    private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>
) : CharactersRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactesrPaging()
    }
}
