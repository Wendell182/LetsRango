package com.letsrango.app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_lets_rango.*
import kotlinx.android.synthetic.main.activity_lets_rango.view.*
import java.time.DateTimeException
import java.util.*
import java.util.regex.Pattern.matches

class LetsRango : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lets_rango)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lets_rango)
        PreferenceManager.getDefaultSharedPreferences(this).apply {



            btnTest.setOnClickListener {
                // btnTest.setTextColor(resources.getColor(R.color.colorBtnText))
                //btnTest.setBackgroundColor(resources.getColor(R.color.colorBtnTestBg))
                //Toast.makeText(this, "whatever", Toast.LENGTH_LONG).show()
                var textTest = editTest.text
                Snackbar.make(btnTest, textTest, Snackbar.LENGTH_LONG).show()

            }


            btnTest.setOnLongClickListener {
                btnTest.setTextColor(resources.getColor(R.color.colorBtnTestLg))
                Snackbar.make(btnTest, "whatever", Snackbar.LENGTH_INDEFINITE).show()

                return@setOnLongClickListener true
            }

            btnFor.setOnClickListener {
                for (i in 0..10) {
                    if (!editTest.text.isEmpty() && editTest.text.toString().matches(Regex("[0-9]+"))) {
                        var countValue = i
                        editTest.setText((editTest.text.toString().toInt() + countValue).toString())
                        editTest.refreshDrawableState()
                        editTest.error = null
                    } else {
                        editTest.error = "Erro, digite um número"
                    }

                }


            }

        }

    }
}
























