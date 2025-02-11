package com.mendelin.androidintegrator.tmdb.domain.model

data class MovieGenre(
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)
