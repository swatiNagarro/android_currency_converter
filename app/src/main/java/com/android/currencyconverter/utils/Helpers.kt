package com.android.currencyconverter.utils

import com.android.currencyconverter.data.response.Rates
import com.android.currencyconverter.data.response.CurrencySymbols
import com.squareup.moshi.Moshi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getHashMapOfCurrencies(it: CurrencySymbols): Map<String, Any>? {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(Any::class.java)
    val jsonStructure = adapter.toJsonValue(it)
    val jsonObject = jsonStructure as Map<String, Any>?
    return jsonObject
}

fun getListOfCurrencySymbols(it: CurrencySymbols): List<String>? {
    var hashMap = getHashMapOfCurrencies(it)
    return hashMap?.keys?.toList()
}

fun getCurrentDate(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatted = current.format(formatter)
    return formatted
}

fun getPopularRatesHashmap(rates: Rates): Map<String, Any> {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(Any::class.java)
    val jsonStructure = adapter.toJsonValue(rates)
    val jsonObject = jsonStructure as Map<String, Any>?
    return jsonObject.orEmpty()
}