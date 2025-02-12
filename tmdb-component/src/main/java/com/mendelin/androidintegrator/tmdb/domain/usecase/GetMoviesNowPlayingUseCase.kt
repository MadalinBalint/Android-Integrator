package com.mendelin.androidintegrator.tmdb.domain.usecase

import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.tmdb.domain.model.NowPlaying
import com.mendelin.androidintegrator.tmdb.domain.repository.TmdbRepository

internal class GetMoviesNowPlayingUseCase(private val tmdbRepository: TmdbRepository) : GetMoviesNowPlaying {
    override suspend fun invoke(page: Int): AiResult<NowPlaying, String> =
        tmdbRepository.getMoviesNowPlaying(page)
}
