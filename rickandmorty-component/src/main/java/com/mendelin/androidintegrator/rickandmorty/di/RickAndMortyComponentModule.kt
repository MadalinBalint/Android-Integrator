package com.mendelin.androidintegrator.rickandmorty.di

import com.apollographql.apollo.ApolloClient
import com.mendelin.androidintegrator.rickandmorty.data.mapper.*
import com.mendelin.androidintegrator.rickandmorty.data.repository.DefaultRickAndMortyRepository
import com.mendelin.androidintegrator.rickandmorty.domain.repository.RickAndMortyRepository
import com.mendelin.androidintegrator.rickandmorty.domain.usecase.*
import com.mendelin.androidintegrator.shared.KoinConstants.IO_DISPATCHER
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val RICK_AND_MORTY_URL = "https://rickandmortyapi.com/graphql"

val rickAndMortyComponentModule = module {
    single {
        ApolloClient.Builder()
            .serverUrl(RICK_AND_MORTY_URL)
            .build()
    }

    single<RickAndMortyMapper> { DefaultRickAndMortyMapper() }

    single<RickAndMortyRepository> {
        DefaultRickAndMortyRepository(
            get(), get(), get(named(IO_DISPATCHER)),
        )
    }

    single<GetCharacters> { GetCharactersUseCase(get()) }

    single<GetEpisodes> { GetEpisodesUseCase(get()) }

    single<GetLocations> { GetLocationsUseCase(get()) }
}
