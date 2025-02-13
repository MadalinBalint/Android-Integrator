package com.mendelin.androidintegrator.tmdb.data.repository

import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.tmdb.data.api.TmdbApi
import com.mendelin.androidintegrator.tmdb.data.mapper.TmdbMapper
import com.mendelin.androidintegrator.tmdb.domain.model.*
import com.mendelin.androidintegrator.tmdb.domain.repository.TmdbRepository
import kotlinx.coroutines.*

internal class DefaultTmdbRepository(
    private val tmdbApi: TmdbApi,
    private val tmdbMapper: TmdbMapper,
    private val ioDispatcher: CoroutineDispatcher
) : TmdbRepository {
    override suspend fun getMovieGenres(): AiResult<List<Genre>, String> {
        return withContext(ioDispatcher) {
            val response = tmdbApi.getMovieGenres()
            response.reduce(
                onSuccess = {
                    AiResult.Success(tmdbMapper.transform(it).genres)
                },
                onFailure = {
                    AiResult.Failure(it)
                }
            )
        }
    }

    override suspend fun getMoviesNowPlaying(page: Int): AiResult<NowPlaying, String> {
        return withContext(ioDispatcher) {
            val response = tmdbApi.getMoviesNowPlaying(page)
            response.reduce(
                onSuccess = {
                    AiResult.Success(tmdbMapper.transform(it))
                },
                onFailure = {
                    AiResult.Failure(it)
                }
            )
        }
    }
}
