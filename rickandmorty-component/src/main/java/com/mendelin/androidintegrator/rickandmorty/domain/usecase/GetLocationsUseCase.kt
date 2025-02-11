package com.mendelin.androidintegrator.rickandmorty.domain.usecase

import com.mendelin.androidintegrator.rickandmorty.domain.model.Locations
import com.mendelin.androidintegrator.rickandmorty.domain.repository.RickAndMortyRepository
import com.mendelin.androidintegrator.shared.AiResult

class GetLocationsUseCase(
    private val rickAndMortyRepository: RickAndMortyRepository
) : GetLocations {
    override suspend fun invoke(page: Int): AiResult<Locations, String> =
        rickAndMortyRepository.getLocations(page)
}
