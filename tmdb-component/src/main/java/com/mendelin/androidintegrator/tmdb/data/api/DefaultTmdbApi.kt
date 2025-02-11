package com.mendelin.androidintegrator.tmdb.data.api

import com.mendelin.androidintegrator.shared.*
import com.mendelin.androidintegrator.tmdb.data.model.*
import io.ktor.client.HttpClient
import io.ktor.client.request.*

class DefaultTmdbApi(private val httpClient: HttpClient) : TmdbApi {
    override suspend fun getMovieGenres(): AiResult<MovieGenreDto, String> =
        ktorResponse {
            httpClient.get("$BASE_URL$MOVIE_GENRES_URL")
        }

    override suspend fun getMoviesNowPlaying(page: Int): AiResult<NowPlayingDto, String> =
        ktorResponse {
            httpClient.get("$BASE_URL$MOVIE_NOW_PLAYING_URL") {
                parameter("page", page)
            }
        }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val MOVIE_NOW_PLAYING_URL = "movie/now_playing"
        const val MOVIE_GENRES_URL = "genre/movie/list"
    }
}
