package com.rrapp

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ✅ SINGLE MDM INSTANCE
        val mdm = MDMManager(this)

        if (mdm.isOwner()) {
            Toast.makeText(this, "Device Owner ACTIVE", Toast.LENGTH_SHORT).show()
        }

        val licenseInput = findViewById<EditText>(R.id.licenseInput)
        val activateBtn = findViewById<Button>(R.id.activateBtn)

        activateBtn.setOnClickListener {

            val licenseKey = licenseInput.text.toString()

            if (licenseKey.isEmpty()) {
                Toast.makeText(this, "Enter License Key", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (licenseKey == "valid_license") {

                scope.launch {
                    val overdueInstallments = true

                    if (overdueInstallments) {
                        runOnUiThread {
                            startActivity(
                                Intent(this@MainActivity, LockActivity::class.java)
                            )
                        }
                    }
                }

            } else {
                Toast.makeText(this, "Invalid License Key", Toast.LENGTH_LONG).show()
            }
        }
    }
}