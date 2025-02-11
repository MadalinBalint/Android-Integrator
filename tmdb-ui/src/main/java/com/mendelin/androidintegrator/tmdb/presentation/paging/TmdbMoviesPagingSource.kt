package com.mendelin.androidintegrator.tmdb.presentation.paging

import androidx.paging.*
import com.mendelin.androidintegrator.tmdb.domain.model.Movie
import com.mendelin.androidintegrator.tmdb.domain.usecase.GetMoviesNowPlaying

class TmdbMoviesPagingSource(
    private val getMoviesNowPlaying: GetMoviesNowPlaying,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val currentPage = params.key ?: 1

        return try {
            getMoviesNowPlaying(currentPage).reduce(
                success = {
                    val maxPages = it.totalPages
                    LoadResult.Page(
                        data = it.results,
                        prevKey = if (currentPage <= 1) null else currentPage.minus(1),
                        nextKey = if (currentPage < maxPages) currentPage.plus(1) else null
                    )
                },

                failure = {
                    LoadResult.Error(Exception(it))
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
