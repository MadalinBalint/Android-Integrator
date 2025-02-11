package com.mendelin.androidintegrator.rickandmorty.presentation

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.*
import coil.compose.AsyncImage
import coil.request.*
import com.mendelin.androidintegrator.designsystem.*
import com.mendelin.androidintegrator.rickandmorty.domain.model.Character
import com.mendelin.androidintegrator.rickandmorty.presentation.viewmodel.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun RickAndMortyScreen(viewModel: RickAndMortyViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val characters = viewModel.characters.state.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getCharactersList()
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CharactersContent(characters, viewModel::updateState)

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        when (state) {
            is RickAndMortyState.Idle -> {}

            is RickAndMortyState.Refresh -> viewModel.getCharactersList()

            is RickAndMortyState.Loading -> ProgressBarContent()

            is RickAndMortyState.Error -> {
                val error = state as RickAndMortyState.Error
                LaunchedEffect(error) {
                    val result = snackbarHostState.showSnackbar(
                        message = error.message,
                        actionLabel = "Retry",
                        withDismissAction = true,
                        duration = SnackbarDuration.Indefinite
                    )

                    when (result) {
                        SnackbarResult.Dismissed -> viewModel.updateState(RickAndMortyState.Idle)
                        SnackbarResult.ActionPerformed -> viewModel.updateState(RickAndMortyState.Refresh)
                    }
                }
            }
        }
    }
}

@Composable
fun CharactersContent(
    characters: LazyPagingItems<Character>,
    updateState: (newState: RickAndMortyState) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = rememberLazyListState()
    ) {
        items(characters.itemCount) { index ->
            val character = characters[index]
            character?.let {
                CharacterItem(it)
            }
        }
    }

    characters.getPagingStates(
        onIdle = {
            updateState(RickAndMortyState.Idle)
        },
        onLoading = {
            updateState(RickAndMortyState.Loading)
        },
        onError = {
            updateState(RickAndMortyState.Error(it))
        }
    )
}

@Composable
fun CharacterItem(character: Character, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .border(1.5.dp, Color.LightGray, RoundedCornerShape(16.dp))

    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .size(144.dp)
        )

        Column(modifier = Modifier.padding(8.dp)) {
            Text(infoString("Name", character.name))
            Text(infoString("Species", character.species))
            Text(infoString("Status", character.status))
            Text(infoString("Gender", character.gender))
            Text(infoString("Location", character.location))
        }
    }
}
