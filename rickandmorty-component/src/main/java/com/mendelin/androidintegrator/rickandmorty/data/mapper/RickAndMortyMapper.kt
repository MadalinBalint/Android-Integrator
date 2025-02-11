package com.mendelin.androidintegrator.rickandmorty.data.mapper

import com.mendelin.androidintegrator.*
import com.mendelin.androidintegrator.rickandmorty.domain.model.*

interface RickAndMortyMapper {
    fun transform(dto: GetCharactersQuery.Info): Info
    fun transform(dto: GetCharactersQuery.Result): Character
    fun transform(dto: GetEpisodesQuery.Info): Info
    fun transform(dto: GetEpisodesQuery.Result): Episode
    fun transform(dto: GetLocationsQuery.Info): Info
    fun transform(dto: GetLocationsQuery.Result): Location
}
