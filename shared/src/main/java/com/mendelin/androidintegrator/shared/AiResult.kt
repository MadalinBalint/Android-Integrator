package com.mendelin.androidintegrator.shared

sealed class AiResult<out S, out F> {
    data class Success<out S>(val data: S) : AiResult<S, Nothing>()
    data class Failure<out F>(val reason: F) : AiResult<Nothing, F>()

    inline fun <R> reduce(success: (S) -> R, failure: (F) -> R): R =
        when (this) {
            is Success -> success(data)
            is Failure -> failure(reason)
        }
}
