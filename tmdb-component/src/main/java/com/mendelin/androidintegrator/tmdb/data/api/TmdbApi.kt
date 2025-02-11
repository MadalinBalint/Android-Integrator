package com.mendelin.androidintegrator.tmdb.data.api

import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.tmdb.data.model.*

interface TmdbApi {
    suspend fun getMovieGenres(): AiResult<MovieGenreDto, String>
    suspend fun getMoviesNowPlaying(page: Int): AiResult<NowPlayingDto, String>
}
