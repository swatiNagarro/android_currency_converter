package com.android.currencyconverter.data

data class RatesResponse(
    val base: String,
    val date: String,
    val historical: Boolean,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int

)