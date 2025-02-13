package com.mendelin.androidintegrator.rickandmorty.presentation.paging

import androidx.paging.*
import com.mendelin.androidintegrator.rickandmorty.domain.model.Character
import com.mendelin.androidintegrator.rickandmorty.domain.usecase.GetCharacters

internal class RamCharactersPagingSource(
    private val getCharacters: GetCharacters,
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val currentPage = params.key ?: 1

        return try {
            getCharacters(currentPage).reduce(
                onSuccess = {
                    val maxPages = it.info.pages
                    LoadResult.Page(
                        data = it.results,
                        prevKey = if (currentPage <= 1) null else currentPage.minus(1),
                        nextKey = if (currentPage < maxPages) currentPage.plus(1) else null
                    )
                },

                onFailure = {
                    LoadResult.Error(Exception(it))
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
