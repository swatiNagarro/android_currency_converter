package com.android.currencyconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.currencyconverter.data.state.NetworkResult
import com.android.currencyconverter.data.response.CurrencySymbolResponse
import com.android.currencyconverter.domain.GetAllCurrencies
import com.android.currencyconverter.utils.getListOfCurrencySymbols
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel
@Inject constructor(private val currencies: GetAllCurrencies) : ViewModel() {

    private var mutableLiveData = MutableLiveData<List<String>>()
    val symbols: LiveData<List<String>>
        get() {
            return mutableLiveData
        }

    private var mutableErrorLiveData = MutableLiveData<Int>()
    val errorLiveData: LiveData<Int>
        get() = mutableErrorLiveData

    init {
        getCurrencySymbols()
    }

    private fun getCurrencySymbols() {
        viewModelScope.launch {
            handleCurrenciesResponse(currencies())
        }
    }

    private fun handleCurrenciesResponse(result: NetworkResult<CurrencySymbolResponse>) {
        when (result) {
            is NetworkResult.Success -> {
                result.value.symbols?.also {
                    mutableLiveData.value =  getListOfCurrencySymbols(it)!! }
            }
            is NetworkResult.Failure -> {
                result.errorCode?.also {
                    mutableErrorLiveData.value = it }
            }

        }
    }

//    private fun getConvertedAmount(from: String, to: String, amount: Double) {
//        viewModelScope.launch {
//            api.getConvertedAmount("2d3d014f31dec44c0bea208a6d2db2a2", from, to, amount)
//        }
//
//    }


}