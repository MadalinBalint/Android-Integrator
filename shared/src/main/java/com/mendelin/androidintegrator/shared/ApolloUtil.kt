package com.mendelin.androidintegrator.shared

import com.apollographql.apollo.api.*

fun <D : Operation.Data> ApolloResponse<D>.getFailureReason(): String? {
    return when {
        exception != null -> exception!!.localizedMessage
        hasErrors() -> errors?.joinToString("\n") ?: "Unknown error"
        data == null -> "Response with null data"
        else -> null
    }
}
