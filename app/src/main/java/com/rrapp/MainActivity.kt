package com.rrapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.*

class MainActivity : Activity() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val licenseInput = findViewById<EditText>(R.id.licenseInput)
        val activateBtn = findViewById<Button>(R.id.activateBtn)

        activateBtn.setOnClickListener {
            val licenseKey = licenseInput.text.toString()

            if (licenseKey.isEmpty()) {
                Toast.makeText(this, "Enter License Key", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Start checking for overdue installments and simulate lock screen trigger
            if (licenseKey == "valid_license") {
                scope.launch {
                    // Simulate overdue installments
                    val overdueInstallments = true // Change this logic based on your requirements

                    if (overdueInstallments) {
                        runOnUiThread {
                            val intent = Intent(this@MainActivity, LockActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Invalid License Key", Toast.LENGTH_LONG).show()
            }
        }
    }
}