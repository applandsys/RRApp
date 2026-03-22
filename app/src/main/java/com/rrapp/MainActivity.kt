package com.rrapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.rrapp.network.ApiClient
import com.rrapp.network.LicenseRequest
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

            scope.launch {
                try {
                    val response = ApiClient.apiService.checkLicense(LicenseRequest(licenseKey))

                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "License Activated", Toast.LENGTH_LONG).show()

                        // start service and pass license
                        val intent = Intent(this@MainActivity, PaymentMonitorService::class.java)
                        intent.putExtra("licenseKey", licenseKey)
                        startService(intent)
                    }

                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "License Invalid", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }
}