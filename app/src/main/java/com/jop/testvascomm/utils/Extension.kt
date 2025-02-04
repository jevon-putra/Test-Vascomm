package com.jop.testvascomm.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Double.numToRp(): String {
    val formatter = DecimalFormat("Rp " + "#,###", DecimalFormatSymbols(Locale("en", "US")))
    return formatter.format(this)
}