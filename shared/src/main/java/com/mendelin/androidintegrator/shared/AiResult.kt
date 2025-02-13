package com.mendelin.androidintegrator.shared

sealed class AiResult<out S, out F> {
    data class Success<out S>(val data: S) : AiResult<S, Nothing>()
    data class Failure<out F>(val reason: F) : AiResult<Nothing, F>()

    inline fun <R> reduce(onSuccess: (S) -> R, onFailure: (F) -> R): R =
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(reason)
        }
}
