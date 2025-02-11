package com.mendelin.androidintegrator.rickandmorty.domain.usecase

import com.mendelin.androidintegrator.rickandmorty.domain.model.Episodes
import com.mendelin.androidintegrator.rickandmorty.domain.repository.RickAndMortyRepository
import com.mendelin.androidintegrator.shared.AiResult

class GetEpisodesUseCase(
    private val rickAndMortyRepository: RickAndMortyRepository
) : GetEpisodes {
    override suspend fun invoke(page: Int): AiResult<Episodes, String> =
        rickAndMortyRepository.getEpisodes(page)
}
