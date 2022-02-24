package com.android.currencyconverter.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.currencyconverter.R
import com.android.currencyconverter.utils.getErrorMessageFromCode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.currency_detail_fragment.*

@AndroidEntryPoint
class CurrencyDetailFragment : Fragment() {

    private val viewModel: CurrencyDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currency_detail_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.rates.observe(this, {
            populateTheUI(it)
        })

        viewModel.errorLiveData.observe(this, {
            context?.let { it1 ->
                var error = getErrorMessageFromCode(it1, it)
                showError(error)

            }
        })
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(
            activity,
            errorMessage,
            Toast.LENGTH_LONG
        ).show()
    }

    @SuppressLint("SetTextI18n")
    private fun populateTheUI(hashMap: Map<String, Any>) {
        hashMap?.let {
            if (it.isNotEmpty()) {
                val keys = it.keys.toList()
                keys.forEach { key ->
                    val textView = TextView(context)
                    val padding = resources.getDimension(R.dimen._5dp).toInt()
                    textView.setPadding(padding, padding, padding, padding)
                    textView.text = " $key: ${it[key]}"
                    layout_rates_other_currencies.addView(textView)
                }

            }
        }

    }


}