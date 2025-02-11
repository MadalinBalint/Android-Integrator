package com.mendelin.androidintegrator.designsystem

import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

fun infoString(key: String, value: String) = buildAnnotatedString {
    withStyle(
        SpanStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    ) {
        append(key)
        append(": ")
    }
    withStyle(
        SpanStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    ) {
        append(value)
    }
}
