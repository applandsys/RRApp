package com.rrapp

import android.app.Service
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.*

class PaymentMonitorService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            monitorPayments()
        }
        return START_STICKY
    }

    private suspend fun monitorPayments() {
        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val admin = ComponentName(this@PaymentMonitorService, MyDeviceAdminReceiver::class.java)

        while (true) {
            delay(60_000) // Check every minute

            val overdue = checkPaymentStatus() // TODO: call backend API

            if (overdue) {
                // Lock device
                dpm.lockNow()
            }
        }
    }

    private fun checkPaymentStatus(): Boolean {
        // TODO: Replace with API call to your server
        // Return true if payment is overdue
        return false
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PaymentMonitorService::class.java)
            context.startService(intent)
        }
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }
}