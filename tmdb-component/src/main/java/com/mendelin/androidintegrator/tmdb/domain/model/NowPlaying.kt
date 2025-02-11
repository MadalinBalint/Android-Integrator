package com.mendelin.androidintegrator.tmdb.domain.model


data class NowPlaying(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val results: List<Movie>
)

data class Movie(
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
)
