package com.fappslab.marvel.framework.di

import com.fappslab.core.usecase.GetCharactersUseCase
import com.fappslab.core.usecase.GetCharactersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindCharactersUseCase(
        useCase: GetCharactersUseCaseImpl
    ): GetCharactersUseCase
}
