package com.mendelin.androidintegrator.binance.data.api

import com.mendelin.androidintegrator.binance.data.model.CryptoAvgPriceDto
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json

internal class DefaultBinanceApi(private val webSocketClient: HttpClient) : BinanceApi {
   private val BASE_URL = "wss://stream.binance.com:9443/ws"

    override fun getAveragePrice(
        symbols: List<String>,
        stableCoin: String
    ): Flow<CryptoAvgPriceDto> {
        val ticker = createTickerList(symbols, stableCoin)

        return callbackFlow {
            webSocketClient.webSocket(urlString = "$BASE_URL$ticker") {
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val json = frame.readText()
                        trySend(Json.decodeFromString<CryptoAvgPriceDto>(json))
                    }
                }

                awaitClose { webSocketClient.close() }
            }
        }
    }

    private fun createTickerList(symbols: List<String>, stableCoin: String): String =
        buildString {
            for (symbol in symbols) {
                append("/${symbol.lowercase()}${stableCoin.lowercase()}@avgPrice")
            }
        }
}
