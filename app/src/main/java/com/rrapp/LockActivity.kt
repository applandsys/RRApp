package com.rrapp

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.*

class LockActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Force the lock screen to stay on top
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
        setContentView(R.layout.lock_screen)

        val input = findViewById<EditText>(R.id.codeInput)
        val unlockBtn = findViewById<Button>(R.id.unlockBtn)

        unlockBtn.setOnClickListener {

            val enteredCode = input.text.toString()

            // Simulate the secret code to unlock
            val correctCode = "1234" // You can change this to whatever code you'd like

            if (enteredCode == correctCode) {
                Toast.makeText(this, "Unlocked!", Toast.LENGTH_SHORT).show()
                finish() // Close the LockActivity and return to the MainActivity or another screen
            } else {
                Toast.makeText(this, "Wrong Code", Toast.LENGTH_SHORT).show()
            }
        }
    }
}