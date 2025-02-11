package com.mendelin.androidintegrator.tmdb.domain.usecase

import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.tmdb.domain.model.*
import com.mendelin.androidintegrator.tmdb.domain.repository.TmdbRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.*

class GetMoviesNowPlayingUseCaseTest {
    private val tmdbRepository: TmdbRepository = mock()
    private val sut = GetMoviesNowPlayingUseCase(tmdbRepository)

    @Test
    fun `EXPECT movie list genres WHEN getMoviesNowPlaying is successful`() = runTest {
        whenever(tmdbRepository.getMoviesNowPlaying(1)).thenReturn(AiResult.Success(NOW_PLAYING))

        val expected = AiResult.Success(NOW_PLAYING)
        val actual = sut(1)

        assertEquals(expected, actual)
    }

    @Test
    fun `EXPECT error WHEN getMoviesNowPlaying fails`() = runTest {
        whenever(tmdbRepository.getMoviesNowPlaying(1)).thenReturn(AiResult.Failure("Failure"))

        val expected = AiResult.Failure("Failure")
        val actual = sut(1)

        assertEquals(expected, actual)
    }

    private companion object {
        val MOVIE_LIST = List(10) {
            Movie(
                backdropPath = if (it % 2 == 0) "https://image.tmdb.org/t/p/original/backdrop_$it.jpg" else "",
                genreIds = listOf(it),
                id = it,
                originalLanguage = "en",
                originalTitle = "Original title $it",
                overview = "Overview no. $it",
                posterPath = if (it % 2 == 0) "https://image.tmdb.org/t/p/original/poster_$it.jpg" else "",
                releaseDate = "01/01/2025",
                title = "Title $it",
            )
        }

        val NOW_PLAYING = NowPlaying(
            page = 1,
            totalPages = 2,
            totalResults = 10,
            results = MOVIE_LIST
        )
    }
}
