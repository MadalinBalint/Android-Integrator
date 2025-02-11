package com.mendelin.androidintegrator.tmdb.domain.usecase

import com.mendelin.androidintegrator.shared.AiResult
import com.mendelin.androidintegrator.tmdb.domain.model.*

fun interface GetMovieGenres {
   suspend operator fun invoke() : AiResult<List<Genre>, String>
}

fun interface GetMoviesNowPlaying {
   suspend operator fun invoke(page: Int): AiResult<NowPlaying, String>
}
