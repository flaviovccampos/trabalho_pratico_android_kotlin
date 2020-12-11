package br.com.codigozeroum.trabalhopraticoandroidkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result_salary.*

class ResultSalary : AppCompatActivity() {

    private lateinit var fullPayment: String;
    private lateinit var dependentsNumber: String;
    private lateinit var otherDiscounts: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_salary)

        fullPayment = intent.getStringExtra(Constants.FULL_PAYMENT) ?: "0"
        dependentsNumber = intent.getStringExtra(Constants.DEPENDENTS_NUMBER) ?: "0"
        otherDiscounts = intent.getStringExtra(Constants.OTHER_DISCOUNTS) ?: "0"

        calculateValues(fullPayment, dependentsNumber)

        btnBack.setOnClickListener { finish() }

    }

    private fun calculateValues(fullPayment: String, dependentsNumber: String) {
        val fullPaymentValue = fullPayment.toFloat()
        val dependentsNumberValue = dependentsNumber.toInt()
        var inssResult = 0f
        var irrfResult = 0f

        //Calculo de INSS
        inssResult = calculateINSS(fullPaymentValue)

        //Calculo IRRF
        irrfResult = calculateIRRF(fullPaymentValue, inssResult, dependentsNumberValue)
        showValues(fullPaymentValue, inssResult, irrfResult, otherDiscounts.toFloat())
    }

    private fun calculateINSS(payment: Float): Float {
        var inssResultValue = 713.10f
        var aliquotaResult = 0f

        if (payment <= 1045) {
            aliquotaResult = payment * 0.075f
            inssResultValue = aliquotaResult
        }
        if (payment > 1045 && payment <= 2089.60) {
            aliquotaResult = payment * 0.09f
            inssResultValue = aliquotaResult - 15.67f
        }
        if (payment > 2098.60 && payment <= 3134.4) {
            aliquotaResult = payment * 0.12f
            inssResultValue = aliquotaResult - 78.36f
        }
        if (payment > 3134.4 && payment <= 6101.06) {
            aliquotaResult = payment * 0.14f
            inssResultValue = aliquotaResult - 141.05f
        }
        return inssResultValue
    }

    private fun calculateIRRF(payment: Float, inss: Float, numDependents: Int): Float {
        val calculateBase = payment - inss - numDependents * 189.59f
        var irrfResult = 0f
        if (calculateBase > 1903.98 && calculateBase <= 2826.65) {
            irrfResult = calculateBase * (7.5f / 100) - 142.80f
        }
        if (calculateBase > 2826.65 && calculateBase <= 3751.05) {
            irrfResult = calculateBase * (15f / 100) - 354.8f
        }
        if (calculateBase > 3751.05 && calculateBase <= 4664.68) {
            irrfResult = calculateBase * (22.5f / 100) - 636.13f
        }
        if (calculateBase > 4664.68) {
            irrfResult = calculateBase * (27.5f / 100) - 869.36f
        }
        return irrfResult
    }

    @SuppressLint("SetTextI18n")
    private fun showValues(fullPaymentValue: Float, inssResult: Float, irrfResult: Float, otherDiscounts: Float) {

        tvFullPaymentResult.text = String.format("%.2f", fullPaymentValue)
        tvINSSResult.text = "-" + String.format("%.2f", inssResult)
        tvINSSResult.text = "-" + String.format("%.2f", inssResult)
        tvIRRFResult.text = "-" + String.format("%.2f", irrfResult)
        tvOtherDiscountsResult.text = String.format("%.2f", otherDiscounts)

        val salaryResult = fullPaymentValue - inssResult - irrfResult - otherDiscounts
        tvLiquidPaymentResult.text = String.format("%.2f", salaryResult)

        val discounts = 100 - salaryResult * 100 / fullPaymentValue
        tvDiscountsResult.text = String.format("%.2f", discounts) + "%"
    }
}