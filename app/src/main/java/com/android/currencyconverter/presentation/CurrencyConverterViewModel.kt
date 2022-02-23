package com.android.currencyconverter.presentation

import com.android.currencyconverter.data.Symbols
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.currencyconverter.data.CurrencyConverterAPI
import com.android.currencyconverter.data.RatesResponse
import com.android.currencyconverter.utils.getCurrentDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel
@Inject constructor(private val api: CurrencyConverterAPI) : ViewModel() {

    private var mutableLiveData = MutableLiveData<Symbols>()
    val symbols: LiveData<Symbols>
        get() {
            return mutableLiveData
        }


    init {
        getCurrencySymbols()
    }

    private fun getCurrencySymbols() {
        viewModelScope.launch {
            mutableLiveData.value = api.getCurrencySymbol("2d3d014f31dec44c0bea208a6d2db2a2")
        }
    }

    private fun getConvertedAmount(from: String, to: String, amount: Double) {
        viewModelScope.launch {
            api.getConvertedAmount("2d3d014f31dec44c0bea208a6d2db2a2", from, to, amount)
        }

    }


}