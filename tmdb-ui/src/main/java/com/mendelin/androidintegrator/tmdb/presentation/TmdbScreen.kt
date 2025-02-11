package com.mendelin.androidintegrator.tmdb.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.*
import coil.compose.AsyncImage
import coil.request.*
import com.mendelin.androidintegrator.designsystem.*
import com.mendelin.androidintegrator.tmdb.domain.model.*
import com.mendelin.androidintegrator.tmdb.presentation.viewmodel.*
import org.koin.androidx.compose.koinViewModel
import com.mendelin.androidintegrator.tmdb.presentation.viewmodel.TmdbViewModel.*

@Composable
fun TmdbScreen(viewModel: TmdbViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val genres by viewModel.genres.state.collectAsStateWithLifecycle()
    val movies = viewModel.movies.state.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getGenres()
    }

    LaunchedEffect(Unit) {
        viewModel.getMovies()
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        MoviesContent(movies, genres)

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        when (state) {
            is TmdbState.Idle -> {}

            is TmdbState.Refresh -> viewModel.getMovies()

            is TmdbState.Loading -> ProgressBarContent()

            is TmdbState.Error -> {
                val error = state as TmdbState.Error
                LaunchedEffect(error) {
                    val result = snackbarHostState.showSnackbar(
                        message = error.message,
                        actionLabel = "Retry",
                        withDismissAction = true,
                        duration = SnackbarDuration.Indefinite
                    )

                    when (result) {
                        SnackbarResult.Dismissed -> viewModel.updateState(TmdbState.Idle)
                        SnackbarResult.ActionPerformed -> viewModel.updateState(TmdbState.Refresh)
                    }
                }
            }
        }
    }

    LaunchedEffect(movies) {
        movies.getPagingStates(
            onIdle = {
                viewModel.updateState(TmdbState.Idle)
            },
            onLoading = {
                viewModel.updateState(TmdbState.Loading)
            },
            onError = {
                viewModel.updateState(TmdbState.Error(it))
            }
        )
    }
}

@Composable
fun MoviesContent(
    movies: LazyPagingItems<Movie>,
    genreList: List<Genre>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = rememberLazyListState()
    ) {
        items(movies.itemCount, key = { it }) { index ->
            val character = movies[index]
            character?.let {
                MovieItem(it, genreList)
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    genreList: List<Genre>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .border(1.5.dp, Color.LightGray, RoundedCornerShape(16.dp))

    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.backdropPath)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
        )

        Column(modifier = Modifier.padding(8.dp)) {
            Text(movie.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            if (movie.overview.isNotEmpty()) {
                Text(movie.overview, fontSize = 12.sp)
            }
            Text(infoString("Release date", movie.releaseDate))
            val movieGenres = getMovieGenres(genreList, movie.genreIds)
            if (movieGenres.isNotEmpty()) {
                Text(infoString("Genres", movieGenres))
            }
        }
    }
}

@Composable
fun getMovieGenres(genreList: List<Genre>, genres: List<Int>): String {
    return genres.mapNotNull { id -> genreList.firstOrNull { it.id == id }?.name }
        .joinToString(", ")
}
