package com.mendelin.androidintegrator.exchangerate.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mendelin.androidintegrator.exchangerate.presentation.viewmodel.ExchangeRateViewModel
import com.mendelin.androidintegrator.shared.round
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ExchangeRateScreen(viewModel: ExchangeRateViewModel = koinViewModel()) {
    val currencyList by viewModel.currencies.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getCurrencyList()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        state = rememberLazyListState()
    ) {
        item {
            Text(
                "Currency Exchange Rate in Romania",
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

        items(currencyList.size) { index ->
            val currency = currencyList[index]
            if (currency.value < 0.04) {
                Text("100 ${currency.currency} = ${currency.value * 100} RON")
            } else {
                Text("1 ${currency.currency} = ${currency.value.round(4)} RON")
            }
        }
    }
}
