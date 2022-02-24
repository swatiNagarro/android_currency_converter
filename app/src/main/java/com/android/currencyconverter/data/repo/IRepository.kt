package com.android.currencyconverter.data.repo

import com.android.currencyconverter.data.state.NetworkResult
import com.android.currencyconverter.data.response.RatesResponse
import com.android.currencyconverter.data.response.CurrencySymbolResponse

interface IRepository {
    suspend fun getAllCurrencySymbols(): NetworkResult<CurrencySymbolResponse>
    suspend fun getRatesForPopularCurrencies(): NetworkResult<RatesResponse>

}