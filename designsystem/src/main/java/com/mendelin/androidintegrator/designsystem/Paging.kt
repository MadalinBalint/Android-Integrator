package com.mendelin.androidintegrator.designsystem

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun LazyPagingItems<*>.getPagingStates(
    onIdle: () -> Unit,
    onLoading: () -> Unit,
    onError: (String) -> Unit,
) {
    when {
        loadState.refresh is LoadState.Error -> {
            val error =
                (loadState.refresh as LoadState.Error).error.localizedMessage ?: "Unknown error"
            onError(error)
        }

        loadState.append is LoadState.Error -> {
            val error =
                (loadState.append as LoadState.Error).error.localizedMessage ?: "Unknown error"
            onError(error)
        }

        loadState.refresh is LoadState.Loading -> onLoading()
        loadState.append is LoadState.Loading -> onLoading()

        loadState.refresh is LoadState.NotLoading -> onIdle()
        loadState.append is LoadState.NotLoading -> onIdle()
    }
}
