package com.android.currencyconverter.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.currencyconverter.R
import com.android.currencyconverter.presentation.base.BaseFragment
import com.android.currencyconverter.utils.getErrorMessageFromCode
import com.android.currencyconverter.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.currency_converter_fragment.*


@AndroidEntryPoint
class CurrencyConverterFragment : BaseFragment(), AdapterView.OnItemSelectedListener {
    private lateinit var selectCurrencyFrom: String
    private lateinit var selectCurrencyTo: String

    private val viewModel: CurrencyConverterViewModel by viewModels()

    override fun observeViewModel() {
        observe(viewModel.symbols, ::prepareCurrencySpinners)
        observe(viewModel.errorLiveData, ::showError)
        observe(viewModel.amount, ::showConvertedAmount)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currency_converter_fragment, container, false)
    }


    override fun onStart() {
        super.onStart()
        selectCurrencyFrom = "AED"
        selectCurrencyTo = "INR"
        amountTextChangeHandling()
        detail_button.setOnClickListener {
            findNavController().navigate(R.id.action_to_detailFragment)
        }
    }

    private fun amountTextChangeHandling() {
        enter_amount_edittext.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    viewModel.getConvertedAmount(selectCurrencyFrom, selectCurrencyTo, s.toString())
                }

            }
        })
    }

    private fun showConvertedAmount(amt: String) {
        converted_amount_edittext.setText(amt)

    }

    private fun showError(errorCode: Int) {
        Toast.makeText(
            activity,
            context?.let { it1 -> getErrorMessageFromCode(it1, errorCode) },
            Toast.LENGTH_LONG
        ).show()
    }

    private fun prepareCurrencySpinners(list: List<String>) {
        from_currency_spinner.onItemSelectedListener = this
        to_currency_spinner.onItemSelectedListener = this
        context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                list,
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                from_currency_spinner.adapter = adapter
                to_currency_spinner.adapter = adapter
            }
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, id: Long) {
        if (parent?.id == R.id.from_currency_spinner) {
            selectCurrencyFrom = parent.getItemAtPosition(pos).toString()
            viewModel.getConvertedAmount(
                selectCurrencyFrom, selectCurrencyTo,
                enter_amount_edittext.text.toString()
            )
        }

        if (parent?.id == R.id.to_currency_spinner) {
            selectCurrencyTo = parent.getItemAtPosition(pos).toString()
            viewModel.getConvertedAmount(
                selectCurrencyFrom, selectCurrencyTo,
                enter_amount_edittext.text.toString()
            )

        }

    }


    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}