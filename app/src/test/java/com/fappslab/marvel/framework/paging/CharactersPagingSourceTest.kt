package com.fappslab.marvel.framework.paging

import androidx.paging.PagingSource
import com.fappslab.core.data.repository.CharactersRemoteDataSource
import com.fappslab.core.domain.model.Character
import com.fappslab.marvel.factory.response.DataWrapperResponseFactory
import com.fappslab.marvel.framework.network.response.DataWrapperResponse
import com.fappslab.testing.MainCoroutineRule
import com.fappslab.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class CharactersPagingSourceTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>

    private val dataWrapperResponseFactory = DataWrapperResponseFactory()

    private val characterFactory = CharacterFactory()

    private val charactersMock = listOf(
        characterFactory.create(CharacterFactory.Hero.ThreeDMan),
        characterFactory.create(CharacterFactory.Hero.ABomb)
    )

    private lateinit var subject: CharactersPagingSource

    @Before
    fun before() {
        subject = CharactersPagingSource(remoteDataSource, "")
    }

    @Test
    fun `getLoadResult When load is called Should return load result`() =
        runBlockingTest {
            // Given
            val expectedResult = PagingSource.LoadResult.Page(
                data = charactersMock,
                prevKey = null,
                20
            )
            whenever(remoteDataSource.fetchCharacters(any()))
                .doReturn(dataWrapperResponseFactory.create())

            // When
            val result = subject.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )

            // Then
            assertEquals(expectedResult, result)
        }

    @Test
    fun `getFailure When load is called Should get a failure`() =
        runBlockingTest {
            // Given
            val expectedResult = RuntimeException("Error to loading")
            whenever(remoteDataSource.fetchCharacters(any())).doThrow(expectedResult)

            // When
            val result = subject.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )

            // Then
            assertEquals(PagingSource.LoadResult.Error<Int, Character>(expectedResult), result)
        }
}
