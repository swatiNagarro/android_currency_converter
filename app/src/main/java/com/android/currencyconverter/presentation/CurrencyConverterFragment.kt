package com.android.currencyconverter.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.currencyconverter.R
import com.android.currencyconverter.utils.getErrorMessageFromCode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.currency_converter_fragment.*


@AndroidEntryPoint
class CurrencyConverterFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var selectCurrencyFrom: String
    private lateinit var selectCurrencyTo: String

    private val viewModel: CurrencyConverterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currency_converter_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.symbols.observe(this, {
            prepareCurrencySpinners(it)
        })

        viewModel.errorLiveData.observe(this, {
            context?.let { it1 ->
                var error = getErrorMessageFromCode(it1, it)
                showError(error)

            }
        })


        detail_button.setOnClickListener {
            findNavController().navigate(R.id.action_to_detailFragment)
        }
    }


    private fun showError(errorMessage: String) {
        Toast.makeText(
            activity,
            errorMessage,
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
            Log.d("test", "Selected from first: ${parent.getItemAtPosition(pos)}")
            selectCurrencyFrom = parent.getItemAtPosition(pos).toString()
        }

        if (parent?.id == R.id.to_currency_spinner) {
            Log.d("test", "Selected from to: ${parent.getItemAtPosition(pos)}")
            selectCurrencyTo = parent.getItemAtPosition(pos).toString()
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}