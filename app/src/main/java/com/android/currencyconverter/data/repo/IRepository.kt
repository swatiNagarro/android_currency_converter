package com.android.currencyconverter.data.repo

import com.android.currencyconverter.data.response.CurrencyRateResponse
import com.android.currencyconverter.data.response.CurrencySymbolResponse
import com.android.currencyconverter.data.response.RatesResponse
import com.android.currencyconverter.data.state.NetworkResult

interface IRepository {
    suspend fun getAllCurrencySymbols(): NetworkResult<CurrencySymbolResponse>
    suspend fun getAllCurrencyRates(): NetworkResult<CurrencyRateResponse>
    suspend fun getRatesForPopularCurrencies(): NetworkResult<RatesResponse>

}