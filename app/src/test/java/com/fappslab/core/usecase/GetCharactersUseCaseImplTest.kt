package com.fappslab.core.usecase

import androidx.paging.PagingConfig
import com.fappslab.core.data.repository.CharactersRepository
import com.fappslab.core.usecase.GetCharactersUseCase.GetCharactersParams
import com.fappslab.testing.MainCoroutineRule
import com.fappslab.testing.model.CharacterFactory
import com.fappslab.testing.pagingsouce.PagingSourceFactory
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var charactersRepository: CharactersRepository

    private lateinit var subject: GetCharactersUseCase

    private val characterFactory = CharacterFactory()

    private val pagingSourceMock = PagingSourceFactory().create(
        listOf(
            characterFactory.create(CharacterFactory.Hero.ThreeDMan)
        )
    )

    @Before
    fun before() {
        subject = GetCharactersUseCaseImpl(charactersRepository)
    }

    @Test
    fun `getPagingData When use case invoke is called Should validate flow data creation`() =
        runBlockingTest {
            // Given
            whenever(charactersRepository.getCharacters("")).doReturn(pagingSourceMock)

            // When
            val result = subject.invoke(GetCharactersParams("", PagingConfig(20)))

            // Then
            verify(charactersRepository).getCharacters("")
            assertNotNull(result.first())
        }
}
