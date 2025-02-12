package com.mendelin.androidintegrator.tmdb.data.mapper

import com.mendelin.androidintegrator.tmdb.data.model.*
import com.mendelin.androidintegrator.tmdb.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class DefaultTmdbMapperTest {
    private val sut: TmdbMapper = DefaultTmdbMapper()

    @Test
    fun `EXPECT movie genre list WHEN mapping MovieGenreDto to MovieGenre`() {
        val actual = sut.transform(MOVIE_GENRE_DTO)

        assertEquals(MOVIE_GENRE, actual)
    }

    @Test
    fun `EXPECT movie list WHEN mapping NowPlayingDto to NowPlaying`() {
        val actual = sut.transform(NOW_PLAYING_DTO)

        assertEquals(NOW_PLAYING, actual)
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
