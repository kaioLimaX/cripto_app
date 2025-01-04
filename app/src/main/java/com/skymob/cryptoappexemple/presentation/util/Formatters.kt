package com.skymob.cryptoappexemple.presentation.util

import kotlin.math.roundToInt

fun Double.roundToTwoDecimalPlaces(): Double {
    return ((this * 100).roundToInt()) / 100.0
}

fun Double.formatAsShortScale(): String {
    return when {
        this >= 1_000_000_000 -> String.format("%.2fB", this / 1_000_000_000)
        this >= 1_000_000 -> String.format("%.2fM", this / 1_000_000)
        this >= 1_000 -> String.format("%.2fK", this / 1_000)
        else -> this.toString()
    }
}

fun String.truncate(maxLength: Int): String {
    return if (this.length > maxLength) {
        this.substring(0, maxLength ) + "..."
    } else {
        this
    }
}