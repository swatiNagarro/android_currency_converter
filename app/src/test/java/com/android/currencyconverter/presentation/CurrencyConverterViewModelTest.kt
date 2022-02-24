package com.android.currencyconverter.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.currencyconverter.data.response.CurrencySymbolResponse
import com.android.currencyconverter.data.state.NetworkResult
import com.android.currencyconverter.domain.GetAllCurrencies
import com.android.currencyconverter.utils.InstantExecutorExtension
import com.android.currencyconverter.utils.MainCoroutineRule
import getCurrencyResponseTestData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class CurrencyConverterViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var currencyConverterViewModel: CurrencyConverterViewModel

    @MockK
    private lateinit var currencies: GetAllCurrencies

    @MockK
    private lateinit var result: NetworkResult<CurrencySymbolResponse>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        currencyConverterViewModel = CurrencyConverterViewModel(currencies)
    }

    @Test
    fun `test get all currencies`() {
        var currencySymbolResponse = getCurrencyResponseTestData()
        coEvery { currencies() } returns NetworkResult.Success(currencySymbolResponse)
        CurrencyConverterViewModel(currencies)
        currencyConverterViewModel.symbols.observeForever {}
        Assert.assertEquals(
            currencyConverterViewModel.symbols.value?.size,
            currencySymbolResponse.symbols.size
        )
        assertEquals(false, currencyConverterViewModel.symbols.value?.isEmpty())
    }


    @Test
    fun `test when received empty currency list`() {
        var currencySymbolResponseEmptyHashMap = getCurrencyResponseTestData()
        coEvery { currencies() } returns NetworkResult.Success(currencySymbolResponseEmptyHashMap)
        CurrencyConverterViewModel(currencies)
        currencyConverterViewModel.symbols.observeForever {}
        assertEquals(true, currencyConverterViewModel.symbols.value?.isEmpty())
    }

    @Test
    fun `test when server unreachable and received error`() {
        coEvery { currencies() } returns NetworkResult.Failure(
            true, 500,
            null
        )
        currencyConverterViewModel = CurrencyConverterViewModel(currencies)
        currencyConverterViewModel.errorLiveData.observeForever {}
        Assert.assertEquals(500, currencyConverterViewModel.errorLiveData.value)
    }
}