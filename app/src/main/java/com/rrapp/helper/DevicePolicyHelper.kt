package com.rrapp.helper

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import com.rrapp.AdminPanelActivity
import com.rrapp.MyDeviceAdminReceiver


class DevicePolicyHelper(activity: AdminPanelActivity) {

    private var dpm: DevicePolicyManager? = null
    private var admin: ComponentName? = null
    private var context: Context? = null

    fun DevicePolicyHelper(context: Context) {
        this.context = context
        this.dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        this.admin = ComponentName(context, MyDeviceAdminReceiver::class.java)
    }

    // 🔒 BLOCK uninstall
    fun blockUninstall() {
        dpm!!.setUninstallBlocked(admin, context!!.getPackageName(), true)
    }

    // 🔓 ALLOW uninstall
    fun allowUninstall() {
        dpm!!.setUninstallBlocked(admin, context!!.getPackageName(), false)
    }

    // check status
    fun isUninstallBlocked(): Boolean {
        return dpm!!.isUninstallBlocked(admin, context!!.getPackageName())
    }
}