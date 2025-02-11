package com.mendelin.androidintegrator.tmdb.domain.usecase

import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.tmdb.domain.model.Genre
import com.mendelin.androidintegrator.tmdb.domain.repository.TmdbRepository

class GetMovieGenresUseCase(private val tmdbRepository: TmdbRepository) : GetMovieGenres {
    override suspend fun invoke(): AiResult<List<Genre>, String> =
        tmdbRepository.getMovieGenres()
}
