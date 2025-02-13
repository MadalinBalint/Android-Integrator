package com.mendelin.androidintegrator.tmdb.data.model

import kotlinx.serialization.*

@Serializable
internal data class MovieGenreDto(
    @SerialName("genres")
    val genres: List<GenreDto>
)

@Serializable
internal data class GenreDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)
