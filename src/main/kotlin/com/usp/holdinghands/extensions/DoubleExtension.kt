package com.usp.holdinghands.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.round(newScale: Int): Double {
    val decimal = BigDecimal(this).setScale(newScale, RoundingMode.HALF_UP)
    return decimal.toDouble()
}
