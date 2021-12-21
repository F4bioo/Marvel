package com.fappslab.marvel.presentation.characters

import androidx.paging.PagingData
import com.fappslab.core.usecase.GetCharactersUseCase
import com.fappslab.testing.MainCoroutineRule
import com.fappslab.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
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
class CharactersViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var subject: CharactersViewModel

    private val characterFactory = CharacterFactory()

    private val pagingDataMock = PagingData.from(
        listOf(
            characterFactory.create(CharacterFactory.Hero.ThreeDMan),
            characterFactory.create(CharacterFactory.Hero.ABomb)
        )
    )

    @Before
    fun before() {
        subject = CharactersViewModel(getCharactersUseCase)
    }

    @Test
    fun `getPagingData When call PagingData Should validate the paging object`() =
        runBlockingTest {
            // Given
            whenever(getCharactersUseCase.invoke(any())).doReturn(flowOf(pagingDataMock))

            // When
            val result = subject.charactersPagingData("")

            // Then
            assertEquals(1, result.count())
        }

    @Test(expected = Throwable::class)
    fun `getFailure When call use case Should throw an exception`() =
        runBlockingTest {
            // Given
            val expectedError = Throwable()
            whenever(getCharactersUseCase.invoke(any())).doThrow(expectedError)

            // Then
            subject.charactersPagingData("")
        }
}
