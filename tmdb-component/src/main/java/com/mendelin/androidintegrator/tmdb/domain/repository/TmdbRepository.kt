package com.mendelin.androidintegrator.tmdb.domain.repository

import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.tmdb.domain.model.*

internal interface TmdbRepository {
   suspend fun getMovieGenres(): AiResult<List<Genre>, String>
   suspend fun getMoviesNowPlaying(page: Int): AiResult<NowPlaying, String>
}
