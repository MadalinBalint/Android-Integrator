package com.mendelin.androidintegrator.rickandmorty.domain.model

data class Locations(
    val info: Info,
    val results: List<Location>
)

data class Location(
    val id: String,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Character>
)
