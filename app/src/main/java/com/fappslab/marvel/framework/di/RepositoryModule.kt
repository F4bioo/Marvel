package com.fappslab.marvel.framework.di

import com.fappslab.core.data.repository.CharactersRemoteDataSource
import com.fappslab.core.data.repository.CharactersRepository
import com.fappslab.marvel.framework.CharactersRepositoryImpl
import com.fappslab.marvel.framework.network.response.DataWrapperResponse
import com.fappslab.marvel.framework.remote.RetrofitCharactersDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCharacterRepository(
        repository: CharactersRepositoryImpl
    ): CharactersRepository

    @Binds
    fun bindRemoteDataSource(
        dataSource: RetrofitCharactersDataSource
    ): CharactersRemoteDataSource<DataWrapperResponse>
}
