package com.android.currencyconverter.domain

import com.android.currencyconverter.data.repo.IRepository
import com.android.currencyconverter.data.response.RatesResponse
import com.android.currencyconverter.data.state.NetworkResult
import javax.inject.Inject

class GetPopularRates @Inject
constructor(private val repo: IRepository) {
    suspend operator fun invoke(): NetworkResult<RatesResponse> {
        return repo.getRatesForPopularCurrencies()

    }

}