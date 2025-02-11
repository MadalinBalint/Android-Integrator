package com.mendelin.androidintegrator.rickandmorty.domain.usecase

import com.mendelin.androidintegrator.rickandmorty.domain.model.*
import com.mendelin.androidintegrator.shared.AiResult

fun interface GetCharacters {
    suspend operator fun invoke(page: Int): AiResult<Characters, String>
}

fun interface GetEpisodes {
    suspend operator fun invoke(page: Int): AiResult<Episodes, String>
}

fun interface GetLocations {
    suspend operator fun invoke(page: Int): AiResult<Locations, String>
}
