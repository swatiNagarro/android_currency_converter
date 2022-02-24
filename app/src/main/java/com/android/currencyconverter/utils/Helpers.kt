package com.android.currencyconverter.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val DATE_FORMAT = "yyyy-MM-dd"

fun getListOfCurrencySymbols(it: Map<String, String>): List<String>? {
    return it?.keys?.toList()
}

fun getCurrentDate(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    return current.format(formatter)
}
