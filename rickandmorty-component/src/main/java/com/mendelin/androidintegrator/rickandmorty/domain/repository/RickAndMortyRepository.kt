package com.mendelin.androidintegrator.rickandmorty.domain.repository

import com.mendelin.androidintegrator.rickandmorty.domain.model.*
import com.mendelin.androidintegrator.shared.AiResult

internal interface RickAndMortyRepository {
    suspend fun getCharacters(page: Int): AiResult<Characters, String>
    suspend fun getEpisodes(page: Int): AiResult<Episodes, String>
    suspend fun getLocations(page: Int): AiResult<Locations, String>
}
