package com.mendelin.androidintegrator.rickandmorty.domain.usecase

import com.mendelin.androidintegrator.rickandmorty.domain.model.Characters
import com.mendelin.androidintegrator.rickandmorty.domain.repository.RickAndMortyRepository
import com.mendelin.androidintegrator.shared.AiResult

internal class GetCharactersUseCase(
    private val rickAndMortyRepository: RickAndMortyRepository
) : GetCharacters {
    override suspend fun invoke(page: Int): AiResult<Characters, String> =
        rickAndMortyRepository.getCharacters(page)
}
