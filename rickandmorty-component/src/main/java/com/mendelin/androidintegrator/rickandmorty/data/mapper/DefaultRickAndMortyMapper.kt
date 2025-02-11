package com.mendelin.androidintegrator.rickandmorty.data.mapper

import com.mendelin.androidintegrator.*
import com.mendelin.androidintegrator.rickandmorty.domain.model.*

class DefaultRickAndMortyMapper : RickAndMortyMapper {
    override fun transform(dto: GetCharactersQuery.Info): Info {
        return Info(
            count = dto.count ?: 0,
            pages = dto.pages ?: 0,
            next = dto.next ?: 0,
            prev = dto.prev ?: 0
        )
    }

    override fun transform(dto: GetCharactersQuery.Result): Character {
        return Character(
            id = dto.id,
            name = dto.name ?: "",
            status = dto.status ?: "",
            species = dto.species ?: "",
            image = dto.image ?: "",
            gender = dto.gender ?: "",
            location = dto.location?.name ?: ""
        )
    }

    override fun transform(dto: GetEpisodesQuery.Info): Info {
        return Info(
            count = dto.count ?: 0,
            pages = dto.pages ?: 0,
            next = dto.next ?: 0,
            prev = dto.prev ?: 0
        )
    }

    override fun transform(dto: GetEpisodesQuery.Result): Episode {
        return Episode(
            id = dto.id,
            name = dto.name ?: "",
            air_date = dto.air_date ?: "",
            characters = dto.characters.mapNotNull { character ->
                if (character != null) {
                    Character(
                        id = character.id,
                        name = character.name ?: "",
                        status = character.status ?: "",
                        species = character.species ?: "",
                        image = character.image ?: "",
                        gender = character.gender ?: "",
                        location = character.location?.name ?: ""
                    )
                } else null
            },
        )
    }

    override fun transform(dto: GetLocationsQuery.Info): Info {
        return Info(
            count = dto.count ?: 0,
            pages = dto.pages ?: 0,
            next = dto.next ?: 0,
            prev = dto.prev ?: 0
        )
    }

    override fun transform(dto: GetLocationsQuery.Result): Location {
        return Location(
            id = dto.id,
            name = dto.name ?: "",
            type = dto.type ?: "",
            dimension = dto.dimension ?: "",
            residents = dto.residents.mapNotNull { character ->
                if (character != null) {
                    Character(
                        id = character.id,
                        name = character.name ?: "",
                        status = character.status ?: "",
                        species = character.species ?: "",
                        image = character.image ?: "",
                        gender = character.gender ?: "",
                        location = character.location?.name ?: ""
                    )
                } else null
            },
        )
    }
}
