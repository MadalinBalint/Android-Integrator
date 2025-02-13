package com.mendelin.androidintegrator.tmdb.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.mendelin.androidintegrator.presentation.viewmodel.*
import com.mendelin.androidintegrator.tmdb.domain.model.*
import com.mendelin.androidintegrator.tmdb.domain.usecase.*
import com.mendelin.androidintegrator.tmdb.presentation.paging.TmdbMoviesPagingSource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class TmdbViewModel(
    private val getMovieGenres: GetMovieGenres,
    private val getMoviesNowPlaying: GetMoviesNowPlaying,
    private val stateDelegate: StateDelegate<TmdbState> = StateDelegate(TmdbState.Idle),
) : ViewModel(), StateViewModel<TmdbViewModel.TmdbState> by stateDelegate {
    val genres: StateDelegate<List<Genre>> = StateDelegate(emptyList())
    val movies: StateDelegate<PagingData<Movie>> = StateDelegate(PagingData.empty())

    fun getGenres() {
        viewModelScope.launch {
            val movieGenres = getMovieGenres()
            movieGenres.reduce(
                onSuccess = {
                    genres.updateState { it }
                },
                onFailure = {
                    Timber.d("Error", it)
                }
            )
        }
    }

    private fun pagingData(): Flow<PagingData<Movie>> =
        Pager(
            PagingConfig(
                pageSize = TMDB_PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = TMDB_PAGE_SIZE
            )
        ) {
            TmdbMoviesPagingSource(getMoviesNowPlaying = getMoviesNowPlaying)
        }.flow.cachedIn(viewModelScope)

    fun getMovies() {
        viewModelScope.launch {
            stateDelegate.updateState { TmdbState.Loading }
            pagingData().collectLatest { data ->
                movies.updateState { data }
                stateDelegate.updateState { TmdbState.Idle }
            }
        }
    }

    fun updateState(newState: TmdbState) {
        stateDelegate.updateState { newState }
    }

    sealed interface TmdbState {
        data object Idle : TmdbState
        data object Refresh : TmdbState
        data object Loading : TmdbState
        data class Error(val message: String) : TmdbState
    }

    companion object {
        const val TMDB_PAGE_SIZE = 20
    }
}


