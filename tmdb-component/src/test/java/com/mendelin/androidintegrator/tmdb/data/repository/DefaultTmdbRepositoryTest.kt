package com.mendelin.androidintegrator.tmdb.data.repository

import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.tmdb.data.api.TmdbApi
import com.mendelin.androidintegrator.tmdb.data.mapper.TmdbMapper
import com.mendelin.androidintegrator.tmdb.data.model.*
import com.mendelin.androidintegrator.tmdb.domain.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultTmdbRepositoryTest {
    private val tmdbApi: TmdbApi = mock()
    private val tmdbMapper: TmdbMapper = mock()
    private val testDispatcher = StandardTestDispatcher()
    private val sut = DefaultTmdbRepository(tmdbApi, tmdbMapper, testDispatcher)

    @Test
    fun `EXPECT MovieGenre WHEN getMovieGenres is successful`() = runTest(testDispatcher) {
        whenever(tmdbApi.getMovieGenres()).thenReturn(AiResult.Success(MOVIE_GENRE_DTO))
        whenever(tmdbMapper.transform(MOVIE_GENRE_DTO)).thenReturn(MOVIE_GENRE)

        val expected = AiResult.Success(MOVIE_GENRE.genres)

        val actual = sut.getMovieGenres()

        advanceUntilIdle()

        assertEquals(expected, actual)
    }

    @Test
    fun `EXPECT error WHEN getMovieGenres fails`() = runTest(testDispatcher) {
        whenever(tmdbApi.getMovieGenres()).thenReturn(AiResult.Failure("Error"))

        val expected = AiResult.Failure("Error")

        val actual = sut.getMovieGenres()

        advanceUntilIdle()

        assertEquals(expected, actual)
    }

    @Test
    fun `EXPECT MovieGenre WHEN getMoviesNowPlaying is successful`() = runTest(testDispatcher) {
        whenever(tmdbApi.getMoviesNowPlaying(1)).thenReturn(AiResult.Success(NOW_PLAYING_DTO))
        whenever(tmdbMapper.transform(NOW_PLAYING_DTO)).thenReturn(NOW_PLAYING)

        val expected = AiResult.Success(NOW_PLAYING)

        val actual = sut.getMoviesNowPlaying(1)

        advanceUntilIdle()

        assertEquals(expected, actual)
    }

    @Test
    fun `EXPECT error WHEN getMoviesNowPlaying fails`() = runTest(testDispatcher) {
        whenever(tmdbApi.getMoviesNowPlaying(1)).thenReturn(AiResult.Failure("Error"))

        val expected = AiResult.Failure("Error")

        val actual = sut.getMoviesNowPlaying(1)

        advanceUntilIdle()

        assertEquals(expected, actual)
    }

    private companion object {
        val GENRE_LIST_DTO = List(10) {
            GenreDto(id = it, name = "Genre $it")
        }

        val MOVIE_GENRE_DTO = MovieGenreDto(
            genres = GENRE_LIST_DTO
        )

        val GENRE_LIST = List(10) {
            Genre(id = it, name = "Genre $it")
        }

        val MOVIE_GENRE = MovieGenre(
            genres = GENRE_LIST
        )

        val MOVIE_LIST_DTO = List(10) {
            MovieDto(
                adult = false,
                backdropPath = if (it % 2 == 0) "/backdrop_$it.jpg" else null,
                genreIds = listOf(it),
                id = it,
                originalLanguage = "en",
                originalTitle = "Original title $it",
                overview = "Overview no. $it",
                posterPath = if (it % 2 == 0) "/poster_$it.jpg" else null,
                releaseDate = "01/01/2025",
                title = "Title $it",
                video = false,
                voteAverage = it.toFloat(),
                voteCount = it * 10,
                popularity = it.toFloat()
            )
        }

        val NOW_PLAYING_DTO = NowPlayingDto(
            page = 1,
            totalPages = 2,
            totalResults = 10,
            results = MOVIE_LIST_DTO
        )

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

