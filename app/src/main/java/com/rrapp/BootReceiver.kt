package com.rrapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import com.rrapp.helper.DevicePolicyHelper


class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.getAction()) {
            val helper = DevicePolicyHelper(context as AdminPanelActivity)

            val shouldLock = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean("lock_state", false)

            if (shouldLock) {
                helper.blockUninstall()
            }
        }
    }
}