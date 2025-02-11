package com.mendelin.androidintegrator.tmdb.data.model

import kotlinx.serialization.*

@Serializable
data class MovieGenreDto(
    @SerialName("genres")
    val genres: List<GenreDto>
)

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)
