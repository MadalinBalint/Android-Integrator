package com.mendelin.androidintegrator.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mendelin.androidintegrator.designsystem.CardItem
import com.mendelin.androidintegrator.home.presentation.viewmodel.HomeViewModel
import com.mendelin.androidintegrator.home.ui.R
import com.mendelin.androidintegrator.analytics.presentation.navigation.NavigationRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CardItem(
                    painter = painterResource(R.drawable.rick_and_morty),
                    imageDescription = "Rick and Morty",
                    text = "GraphQL endpoint\nRick and Morty is an American adult animated science fiction sitcom that follows the misadventures of Rick Sanchez, a cynical mad scientist, and his good-hearted but fretful grandson Morty Smith, who split their time between domestic life and interdimensional adventures.",
                    onClick = {
                        viewModel.track("home_screen", mapOf("click" to "Rick and Morty"))
                        navController.navigate(NavigationRoutes.RickAndMorty)
                    }
                )
            }

            item {
                CardItem(
                    painter = painterResource(R.drawable.tmdb),
                    imageDescription = "The Movie DB",
                    text = "REST API endpoints\nThe Movie Database (TMDB) is a community built movie and TV database. Every piece of data has been added by our amazing community dating back to 2008.",
                    onClick = {
                        viewModel.track("home_screen", mapOf("click" to "TMDB"))
                        navController.navigate(NavigationRoutes.TMDB)
                    }
                )
            }

            item {
                CardItem(
                    painter = painterResource(R.drawable.exchange_rate),
                    imageDescription = "Exchange rate",
                    text = "SOAP XML endpoint\nExchange rates for multiple currencies from http://www.infovalutar.ro",
                    onClick = {
                        viewModel.track("home_screen", mapOf("click" to "Exchange rate"))
                        navController.navigate(NavigationRoutes.ExchangeRate)
                    }
                )
            }

            item {
                CardItem(
                    painter = painterResource(R.drawable.binance),
                    imageDescription = "Binance crypto",
                    text = "WebSockets endpoint\nBinance Holdings Ltd., branded Binance, is a global company that operates the largest cryptocurrency exchange in terms of daily trading volume of cryptocurrencies. Binance was founded in 2017 by Changpeng Zhao, a developer who had previously created high-frequency trading software.",
                    onClick = {
                        viewModel.track("home_screen", mapOf("click" to "Binance"))
                        navController.navigate(NavigationRoutes.Binance)
                    }
                )
            }
        }
    }
}
