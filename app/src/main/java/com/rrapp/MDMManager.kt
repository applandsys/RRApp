package com.rrapp

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context

class MDMManager(private val context: Context) {

    private val dpm =
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

    private val admin =
        ComponentName(context, MyDeviceAdminReceiver::class.java)

    fun isOwner(): Boolean =
        dpm.isDeviceOwnerApp(context.packageName)

    fun blockUninstall() {
        if (isOwner()) {
            dpm.setUninstallBlocked(admin, context.packageName, true)
        }
    }

    fun allowUninstall() {
        if (isOwner()) {
            dpm.setUninstallBlocked(admin, context.packageName, false)
        }
    }

    fun lockDevice() {
        if (isOwner()) dpm.lockNow()
    }
}