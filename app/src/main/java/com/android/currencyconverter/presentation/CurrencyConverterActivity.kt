package com.android.currencyconverter.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.currencyconverter.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConverterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}