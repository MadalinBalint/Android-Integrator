package com.mendelin.androidintegrator.rickandmorty.data.repository

import com.apollographql.apollo.ApolloClient
import com.mendelin.androidintegrator.*
import com.mendelin.androidintegrator.rickandmorty.data.mapper.RickAndMortyMapper
import com.mendelin.androidintegrator.rickandmorty.domain.model.*
import com.mendelin.androidintegrator.rickandmorty.domain.repository.RickAndMortyRepository
import com.mendelin.androidintegrator.shared.*
import kotlinx.coroutines.*

class DefaultRickAndMortyRepository(
    private val apolloClient: ApolloClient,
    private val ramMapper: RickAndMortyMapper,
    private val ioDispatcher: CoroutineDispatcher
) : RickAndMortyRepository {
    override suspend fun getCharacters(page: Int): AiResult<Characters, String> =
        withContext(ioDispatcher) {
            val response = apolloClient.query(GetCharactersQuery(page = page)).execute()

            val failureReason = response.getFailureReason()
            if (failureReason != null) {
                return@withContext AiResult.Failure(failureReason)
            }

            val info = response.data?.characters?.info?.let {
                ramMapper.transform(it)
            } ?: Info()

            val characters = response.data?.characters?.results?.mapNotNull { character ->
                character?.let {
                    ramMapper.transform(it)
                }
            } ?: emptyList()

            AiResult.Success(
                Characters(
                    info = info,
                    results = characters
                )
            )
        }

    override suspend fun getEpisodes(page: Int): AiResult<Episodes, String> =
        withContext(ioDispatcher) {
            val response = apolloClient.query(GetEpisodesQuery(page = page)).execute()

            val failureReason = response.getFailureReason()
            if (failureReason != null) {
                return@withContext AiResult.Failure(failureReason)
            }

            val info = response.data?.episodes?.info?.let {
                ramMapper.transform(it)
            } ?: Info()

            val episodes = response.data?.episodes?.results?.mapNotNull { episode ->
                episode?.let {
                    ramMapper.transform(it)
                }
            } ?: emptyList()

            AiResult.Success(
                Episodes(
                    info = info,
                    results = episodes
                )
            )
        }

    override suspend fun getLocations(page: Int): AiResult<Locations, String> =
        withContext(ioDispatcher) {
            val response = apolloClient.query(GetLocationsQuery(page = page)).execute()

            val failureReason = response.getFailureReason()
            if (failureReason != null) {
                return@withContext AiResult.Failure(failureReason)
            }

            val info = response.data?.locations?.info?.let {
                ramMapper.transform(it)
            } ?: Info()

            val locations = response.data?.locations?.results?.mapNotNull { location ->
                location?.let {
                    ramMapper.transform(it)
                }
            } ?: emptyList()

            AiResult.Success(
                Locations(
                    info = info,
                    results = locations
                )
            )
        }
}
