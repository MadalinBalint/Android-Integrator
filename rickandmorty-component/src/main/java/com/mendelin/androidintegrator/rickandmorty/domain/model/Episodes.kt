package com.mendelin.androidintegrator.rickandmorty.domain.model

data class Episodes(
    val info: Info,
    val results: List<Episode>
)

data class Episode(
    val id: String,
    val name: String,
    val air_date: String,
    val characters: List<Character>
)
