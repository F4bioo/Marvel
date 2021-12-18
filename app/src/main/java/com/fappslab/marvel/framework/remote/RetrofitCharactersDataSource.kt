package com.fappslab.marvel.framework.remote

import com.fappslab.core.data.repository.CharactersRemoteDataSource
import com.fappslab.marvel.framework.network.MarvelApi
import com.fappslab.marvel.framework.network.response.DataWrapperResponse
import javax.inject.Inject

class RetrofitCharactersDataSource
@Inject
constructor(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource<DataWrapperResponse> {

    override suspend fun fetchCharacters(queries: Map<String, String>): DataWrapperResponse {
        return marvelApi.getCharacters(queries)
    }
}
