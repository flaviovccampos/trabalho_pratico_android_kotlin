package br.com.codigozeroum.trabalhopraticoandroidkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnCalculate.setOnClickListener(View.OnClickListener {
            if (etFullPayment.text.toString().isEmpty()) {
                toastValidation("Salário")
                return@OnClickListener
            }
            if (etDependentsNumber.text.toString().isEmpty()) {
                toastValidation("Qtd de Dependentes")
                return@OnClickListener
            }
            if (etOtherDiscounts.text.toString().isEmpty()) {
                toastValidation("Outros Descontos")
                return@OnClickListener
            }
            val intent = Intent(applicationContext, ResultSalary::class.java)
            intent.putExtra(Constants.FULL_PAYMENT, etFullPayment.text.toString())
            intent.putExtra(Constants.DEPENDENTS_NUMBER, etDependentsNumber.text.toString())
            intent.putExtra(Constants.OTHER_DISCOUNTS, etOtherDiscounts.text.toString())
            startActivity(intent)
        })

    }

    private fun toastValidation(type: String) {
        Toast.makeText(applicationContext, "Favor Preencher $type", Toast.LENGTH_LONG).show()
    }
}