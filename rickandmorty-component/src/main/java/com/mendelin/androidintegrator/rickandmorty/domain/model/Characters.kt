package com.mendelin.androidintegrator.rickandmorty.domain.model

data class Characters(
    val info: Info,
    val results: List<Character>
)

data class Character(
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val gender: String,
    val location: String
)
