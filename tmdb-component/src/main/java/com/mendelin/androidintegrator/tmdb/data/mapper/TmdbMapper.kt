package com.mendelin.androidintegrator.tmdb.data.mapper

import com.mendelin.androidintegrator.tmdb.data.model.*
import com.mendelin.androidintegrator.tmdb.domain.model.*

interface TmdbMapper {
    fun transform(dto: MovieGenreDto): MovieGenre
    fun transform(dto: GenreDto): Genre
    fun transform(dto: NowPlayingDto): NowPlaying
    fun transform(dto: MovieDto): Movie
}
