package luizhvicari.tiptime

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import luizhvicari.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalculate.setOnClickListener{
            buttonCalculateButtonOnClick()
        }

        if (savedInstanceState != null) {
            val tip = savedInstanceState.getString("tip")
            binding.tipResult.text = tip
        } else {
            binding.tipResult.text = getString(R.string.tip_amount_s, "-")
        }

    }

    private fun buttonCalculateButtonOnClick() {
        val tipValue = binding.costOfService.text.toString()
        val cost = tipValue.toDoubleOrNull() ?: return
        val selectedId = binding.tipOption.checkedRadioButtonId
        val tipPercentage = when(selectedId){
            R.id.option_twenty_percent -> .2
            R.id.option_eighteen_percent -> .18
            else -> .15
        }

        var tip = cost * tipPercentage
        if (binding.switchRoundUp.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip =NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount_s, formattedTip)

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("tip", binding.tipResult.text.toString())
    }
}