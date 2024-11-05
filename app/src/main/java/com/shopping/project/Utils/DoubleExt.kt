package com.shopping.project.Utils

import java.text.DecimalFormat
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun Double?.orNull(defaultNull: Double = 0.0): Double = this ?: defaultNull

@OptIn(ExperimentalContracts::class)
fun Double?.isNullOrZero(): Boolean {
    contract { returns(false) implies (this@isNullOrZero != null) }
    return this == null || this == 0.0
}

fun Double.removeDecimalIfRounded(): String {
    return if (this % 1.0 != 0.0) {
        this.toString()
    } else {
        DecimalFormat("#").format(this)
    }
}