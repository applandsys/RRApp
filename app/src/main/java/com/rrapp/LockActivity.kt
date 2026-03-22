package com.rrapp

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.rrapp.database.AppDatabase
import kotlinx.coroutines.*

class LockActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 🔒 Force lock screen to appear over system lock
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
        setContentView(R.layout.lock_screen)

        val input = findViewById<EditText>(R.id.codeInput)
        val btn = findViewById<Button>(R.id.unlockBtn)

        btn.setOnClickListener {

            val code = input.text.toString()

            CoroutineScope(Dispatchers.IO).launch {

                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "rrapp_db"
                ).build()

                val installments =
                    db.installmentDao().getUnpaid()

                val match = installments.any {
                    it.secret_code == code
                }

                runOnUiThread {

                    if (match) {

                        Toast.makeText(
                            this@LockActivity,
                            "Unlocked",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()

                    } else {

                        Toast.makeText(
                            this@LockActivity,
                            "Wrong Code",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }

            }

        }

    }

}