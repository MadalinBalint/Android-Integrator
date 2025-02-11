package com.mendelin.androidintegrator.shared

import io.ktor.client.call.body
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.http.isSuccess

suspend inline fun <reified T> ktorResponse(block: () -> HttpResponse): AiResult<T, String> {
    return try {
        val response = block()

        if (response.status.isSuccess()) {
            val result = response.body<T>()
            AiResult.Success(result)
        } else {
            AiResult.Failure("Error: ${response.status.value} - ${response.status.description}")
        }
    } catch (e: ClientRequestException) {
        // 4xx errors
        AiResult.Failure("Client error: ${e.response.status.value}")
    } catch (e: ServerResponseException) {
        // 5xx errors
        AiResult.Failure("Server error: ${e.response.status.value}")
    } catch (e: Exception) {
        // Other errors (e.g., no internet, timeout)
        AiResult.Failure("Unexpected error: ${e.localizedMessage}")
    }
}

suspend inline fun <reified T> ktorResponseXml(
    deserialize: (String) -> T,
    block: () -> HttpResponse
): AiResult<T, String> {
    return try {
        val response = block()

        if (response.status.isSuccess()) {
            val result = response.bodyAsText()
            AiResult.Success(deserialize(result))
        } else {
            AiResult.Failure("Error: ${response.status.value} - ${response.status.description}")
        }
    } catch (e: ClientRequestException) {
        // 4xx errors
        AiResult.Failure("Client error: ${e.response.status.value}")
    } catch (e: ServerResponseException) {
        // 5xx errors
        AiResult.Failure("Server error: ${e.response.status.value}")
    } catch (e: Exception) {
        // Other errors (e.g., no internet, timeout)
        AiResult.Failure("Unexpected error: ${e.localizedMessage}")
    }
}
