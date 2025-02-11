package com.mendelin.androidintegrator.rickandmorty.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.mendelin.androidintegrator.presentation.viewmodel.*
import com.mendelin.androidintegrator.rickandmorty.domain.model.Character
import com.mendelin.androidintegrator.rickandmorty.domain.usecase.GetCharacters
import com.mendelin.androidintegrator.rickandmorty.presentation.paging.RamCharactersPagingSource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RickAndMortyViewModel(
    private val getCharacters: GetCharacters,
    private val stateDelegate: StateDelegate<RickAndMortyState> = StateDelegate(RickAndMortyState.Idle),
) : ViewModel(), StateViewModel<RickAndMortyState> by stateDelegate {
    val characters: StateDelegate<PagingData<Character>> = StateDelegate(PagingData.empty())

    private fun pagingData(): Flow<PagingData<Character>> =
        Pager(PagingConfig(pageSize = 20, enablePlaceholders = true)) {
            RamCharactersPagingSource(getCharacters = getCharacters)
        }.flow.cachedIn(viewModelScope)

    fun getCharactersList() {
        viewModelScope.launch {
            stateDelegate.updateState { RickAndMortyState.Loading }
            pagingData().collectLatest { data ->
                characters.updateState { data }
                stateDelegate.updateState { RickAndMortyState.Idle }
            }
        }
    }

    fun updateState(newState: RickAndMortyState) {
        stateDelegate.updateState { newState }
    }
}

sealed interface RickAndMortyState {
    data object Idle : RickAndMortyState
    data object Refresh : RickAndMortyState
    data object Loading : RickAndMortyState
    data class Error(val message: String) : RickAndMortyState
}
