package com.shopping.project.Utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun Float?.orNull(defaultNull: Float = 0f): Float = this ?: defaultNull

@OptIn(ExperimentalContracts::class)
fun Float?.isNullOrZero(): Boolean {
    contract { returns(false) implies (this@isNullOrZero != null) }
    return this == null || this == 0f
}
