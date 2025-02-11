package com.mendelin.androidintegrator.tmdb.data.mapper

import com.mendelin.androidintegrator.tmdb.data.model.*
import com.mendelin.androidintegrator.tmdb.domain.model.*

class DefaultTmdbMapper : TmdbMapper {
    override fun transform(dto: MovieGenreDto): MovieGenre {
        return MovieGenre(
            genres = dto.genres.map(::transform)
        )
    }

    override fun transform(dto: GenreDto): Genre {
        return Genre(dto.id, dto.name)
    }

    override fun transform(dto: NowPlayingDto): NowPlaying {
        return NowPlaying(
            page = dto.page,
            totalPages = dto.totalPages,
            totalResults = dto.totalResults,
            results = dto.results.map(::transform)
        )
    }

    override fun transform(dto: MovieDto): Movie {
        return Movie(
            backdropPath = dto.backdropPath?.let {
                "https://image.tmdb.org/t/p/original$it"
            } ?: "",
            genreIds = dto.genreIds,
            id = dto.id,
            originalLanguage = dto.originalLanguage,
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            posterPath = dto.posterPath?.let {
                "https://image.tmdb.org/t/p/original$it"
            } ?: "",
            releaseDate = dto.releaseDate,
            title = dto.title,
        )
    }
}
