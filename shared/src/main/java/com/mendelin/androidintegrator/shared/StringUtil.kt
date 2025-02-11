package com.mendelin.androidintegrator.shared

fun Double.round(decimals: Int = 8) = "%.${decimals}f".format(this)
