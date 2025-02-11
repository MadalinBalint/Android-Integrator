package com.mendelin.androidintegrator.binance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.*
import androidx.lifecycle.compose.*
import com.mendelin.androidintegrator.binance.presentation.viewmodel.BinanceViewModel
import com.mendelin.androidintegrator.shared.round
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun BinanceScreen(viewModel: BinanceViewModel = koinViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.getInitialConfig()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.stopResults()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.observeStableCoin()
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CryptoAvgPriceContent(state)
    }
}

@Composable
fun CryptoAvgPriceContent(priceMap: Map<String, Double>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = rememberLazyListState()
    ) {
        item {
            Text(
                "Binance cryptocurrencies average price",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            val formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy")
            val current = LocalDateTime.now().format(formatter)

            Text(
                current,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        items(priceMap.size) { index ->
            val key = priceMap.keys.elementAt(index)
            Text("$key = ${priceMap[key]!!.round()}")
        }
    }
}


