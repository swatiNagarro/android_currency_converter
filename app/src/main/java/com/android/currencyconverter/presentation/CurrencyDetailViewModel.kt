package com.android.currencyconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.currencyconverter.data.CurrencyConverterAPI
import com.android.currencyconverter.data.RatesResponse
import com.android.currencyconverter.utils.BASE_CURRENCY
import com.android.currencyconverter.utils.POPULAR_CURRENCIES
import com.android.currencyconverter.utils.getCurrentDate
import com.android.currencyconverter.utils.getPopularRatesHashmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailViewModel @Inject constructor(private val api: CurrencyConverterAPI) :
    ViewModel() {

    private var mutableLiveData = MutableLiveData<RatesResponse>()
    val rates: LiveData<RatesResponse>
        get() {
            return mutableLiveData
        }

    init {
        getRates()
    }

    private fun getRates() {
        viewModelScope.launch {
            mutableLiveData.value = api.getRatesForPopularCurrencies(
                getCurrentDate(),
                "2d3d014f31dec44c0bea208a6d2db2a2",
                BASE_CURRENCY, POPULAR_CURRENCIES
            )
        }



    }
}