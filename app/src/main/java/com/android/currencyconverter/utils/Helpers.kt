package com.android.currencyconverter.utils

import com.squareup.moshi.Moshi
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

fun getPopularRatesHashmap(rates: Rates): Map<String, Any> {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(Any::class.java)
    val jsonStructure = adapter.toJsonValue(rates)
    val jsonObject = jsonStructure as Map<String, Any>?
    return jsonObject.orEmpty()
}