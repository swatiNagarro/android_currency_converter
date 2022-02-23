package com.android.currencyconverter.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyConverterAPI {
    @GET("symbols")
    suspend fun getCurrencySymbol(@Query("access_key") access_key: String):
            Symbols

    @GET("convert")
    suspend fun getConvertedAmount(
        @Query("access_key") access_key: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    )

    @GET("{dateInUrl}")
    suspend fun getRatesForPopularCurrencies(
        @Path(value = "dateInUrl", encoded = true) dateInUrl: String,
        @Query("access_key") access_key: String,
        @Query(" base ") base: String,
        @Query(" symbols") symbols: String
    ): RatesResponse
}