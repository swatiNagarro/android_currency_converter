package com.android.currencyconverter.data.repo

import com.android.currencyconverter.BuildConfig
import com.android.currencyconverter.data.api.CurrencyConverterAPI
import com.android.currencyconverter.data.base.BaseRepository
import com.android.currencyconverter.data.response.CurrencySymbolResponse
import com.android.currencyconverter.utils.BASE_CURRENCY
import javax.inject.Inject

class CurrencyRepo @Inject
constructor(private val api: CurrencyConverterAPI) : IRepository, BaseRepository() {
    override suspend fun getAllCurrencySymbols() :CurrencySymbolResponse {
        return api.getCurrencySymbol(BuildConfig.API_KEY)
    }

    override suspend fun getAllCurrencyRates() = safeApiCall {
        api.getAllCurrencyRates(BuildConfig.API_KEY, BASE_CURRENCY)
    }

    override suspend fun getRatesForPopularCurrencies(dateInUrl: String, popularCurrency: String) =
        safeApiCall {
            api.getRatesForPopularCurrencies(
                dateInUrl, BuildConfig.API_KEY,
                BASE_CURRENCY, popularCurrency
            )
        }
}