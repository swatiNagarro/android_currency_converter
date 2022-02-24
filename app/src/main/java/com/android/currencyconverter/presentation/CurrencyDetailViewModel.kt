package com.android.currencyconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.currencyconverter.data.response.RatesResponse
import com.android.currencyconverter.data.state.NetworkResult
import com.android.currencyconverter.domain.GetPopularRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailViewModel
@Inject constructor(private val popularRates: GetPopularRates) :
    ViewModel() {

    private var mutableLiveData = MutableLiveData<Map<String, Any>>()
    val rates: LiveData<Map<String, Any>>
        get() {
            return mutableLiveData
        }
    private var mutableErrorLiveData = MutableLiveData<Int>()

    val errorLiveData: LiveData<Int>
        get() = mutableErrorLiveData

    init {
        getRates()
    }

    private fun getRates() {
        viewModelScope.launch {
            handleCurrenciesResponse(popularRates())
        }
    }

    private fun handleCurrenciesResponse(result: NetworkResult<RatesResponse>) {
        when (result) {
            is NetworkResult.Success -> {
                result.value?.also {
                    mutableLiveData.value = it.rates
                }
            }
            is NetworkResult.Failure -> {
                result.errorCode?.also {
                    mutableErrorLiveData.value = it
                }
            }

        }
    }
}