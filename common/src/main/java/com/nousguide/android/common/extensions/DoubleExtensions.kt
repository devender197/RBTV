package com.nousguide.android.common.extensions

import java.text.DecimalFormat

fun Double.toPrice(): String {
    val pattern = "#,###.##"
    val decimalFormat = DecimalFormat(pattern).apply {
        groupingSize = 3
    }
    return "$${decimalFormat.format(this)}"
}
