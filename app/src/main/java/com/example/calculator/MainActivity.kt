package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.kaen.dagger.BadSyntaxException
import io.kaen.dagger.ExpressionParser
import java.lang.Double.parseDouble

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//        //Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_main)

    }

    private val OPERATOR = "OPERATOR";
    private val OPERAND = "OPERAND"
    private var exp: String = "";
    private var temp: String = "";
    private var previous: String = "OPERAND"
    private var firstTime: Boolean = true

    public fun operator(view: View) {
        val resultView: TextView = findViewById(R.id.result)
        val expView: TextView = findViewById(R.id.exp)
        val button: Button = view as Button
        if (previous != OPERATOR) {
            previous = OPERATOR
            exp += temp + button.text.toString()
            temp = ""
        }
        expView.text = exp
        resultView.text = button.text.toString()
    }

    public fun operand(view: View) {
        val resultView: TextView = findViewById(R.id.result)
        val expView: TextView = findViewById(R.id.exp)
        val button: Button = view as Button
        previous = OPERAND
        if (firstTime) {
            firstTime = false
            temp += button.text.toString()
        } else {
            temp += button.text.toString()
        }
        expView.text = exp + temp
        resultView.text = temp
    }


    public fun clear(view: View) {
        temp = ""
        exp = ""
        firstTime = true
        previous = OPERAND
        val resultView: TextView = findViewById(R.id.result)
        val expView: TextView = findViewById(R.id.exp)
        resultView.text = "0.0"
        expView.text = ""
    }

    public fun compute(view: View) {
        exp += temp
        val resultView: TextView = findViewById(R.id.result)
        val expView: TextView = findViewById(R.id.exp)
        if (exp.isEmpty()) {
            resultView.text = "0"
            expView.text = ""
        } else {
            val parser = ExpressionParser()
            try {
                resultView.text = parser.evaluate(exp).toString()
                expView.text = ""
            }
            catch (e: Exception) {
                clear(findViewById(R.id.ac))
                Toast.makeText(this, "Oops! Bad Expression!", Toast.LENGTH_SHORT).show()
            }
        }
        exp = ""
        temp = ""
    }

}