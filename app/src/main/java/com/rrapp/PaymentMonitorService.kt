package com.rrapp

import android.app.Notification
import android.app.Service
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.room.Room
import com.rrapp.database.AppDatabase
import com.rrapp.repository.InstallmentRepository
import com.rrapp.network.ApiClient
import com.rrapp.network.LicenseRequest
import kotlinx.coroutines.*
import java.time.LocalDate

class PaymentMonitorService : Service() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        val notification = Notification.Builder(this, "RRChannel")
            .setContentTitle("RRApp Monitoring")
            .setContentText("Monitoring overdue payments")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val licenseKey = intent?.getStringExtra("licenseKey") ?: ""

        scope.launch {
            monitor(licenseKey)
        }

        return START_STICKY
    }

    private suspend fun monitor(licenseKey: String) {

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "rrapp_db"
        ).build()

        val repo = InstallmentRepository(db.installmentDao())

        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val admin = ComponentName(this, MyDeviceAdminReceiver::class.java)

        while (true) {

            try {
                val response = ApiClient.apiService.checkLicense(LicenseRequest(licenseKey))
                repo.saveApiData(response)
            } catch (e: Exception) {
                // offline mode
            }

            val installments = db.installmentDao().getUnpaid()
            val today = LocalDate.now()

            installments.forEach {
                val dueDate = LocalDate.parse(it.due_date)
                if (dueDate.isBefore(today)) {
                    val intent = Intent(this, LockActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }

            delay(60 * 60 * 1000) // 1 hour
        }
    }
}