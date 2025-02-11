package com.mendelin.androidintegrator.tmdb.domain.usecase

import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.tmdb.domain.model.Genre
import com.mendelin.androidintegrator.tmdb.domain.repository.TmdbRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.*

class GetMovieGenresUseCaseTest {
    private val tmdbRepository: TmdbRepository = mock()
    private val sut = GetMovieGenresUseCase(tmdbRepository)

    @Test
    fun `EXPECT movie genres WHEN getMovieGenres is successful`() = runTest {
        whenever(tmdbRepository.getMovieGenres()).thenReturn(AiResult.Success(GENRE_LIST))

        val expected = AiResult.Success(GENRE_LIST)
        val actual = sut()

        assertEquals(expected, actual)
    }

    @Test
    fun `EXPECT error WHEN getMovieGenres fails`() = runTest {
        whenever(tmdbRepository.getMovieGenres()).thenReturn(AiResult.Failure("Failure"))

        val expected = AiResult.Failure("Failure")
        val actual = sut()

        assertEquals(expected, actual)
    }

    private companion object {
        val GENRE_LIST = List(10) {
            Genre(id = it, name = "Genre $it")
        }
    }
}
