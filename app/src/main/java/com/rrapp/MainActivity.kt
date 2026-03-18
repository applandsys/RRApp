package com.rrapp

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var dpm: DevicePolicyManager
    private lateinit var adminComponent: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(this, MyDeviceAdminReceiver::class.java)

        val licenseInput = findViewById<EditText>(R.id.licenseInput)
        val activateBtn = findViewById<Button>(R.id.activateBtn)

        activateBtn.setOnClickListener {
            val licenseKey = licenseInput.text.toString()
            if (verifyLicenseKey(licenseKey)) {
                Toast.makeText(this, "License Activated!", Toast.LENGTH_SHORT).show()
                // Start monitoring service
                PaymentMonitorService.start(this)
            } else {
                Toast.makeText(this, "Invalid License Key", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyLicenseKey(key: String): Boolean {
        // TODO: Replace with API call to your backend
        return key.startsWith("RR-")
    }
}