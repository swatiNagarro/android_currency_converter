package com.android.currencyconverter.data.repo

import com.android.currencyconverter.data.api.CurrencyConverterAPI
import com.android.currencyconverter.data.base.BaseRepository
import com.android.currencyconverter.utils.BASE_CURRENCY
import com.android.currencyconverter.utils.POPULAR_CURRENCIES
import com.android.currencyconverter.utils.getCurrentDate
import javax.inject.Inject

class CurrencyRepo @Inject
constructor(private val api: CurrencyConverterAPI) : IRepository, BaseRepository() {
    override suspend fun getAllCurrencySymbols() = safeApiCall {
        api.getCurrencySymbol("2d3d014f31dec44c0bea208a6d2db2a2")
    }

    override suspend fun getRatesForPopularCurrencies() = safeApiCall {
        api.getRatesForPopularCurrencies(
            getCurrentDate(), "2d3d014f31dec44c0bea208a6d2db2a2",
            BASE_CURRENCY, POPULAR_CURRENCIES
        )
    }
}