package com.rrapp

import android.app.Activity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import com.rrapp.helper.DevicePolicyHelper


class AdminPanelActivity : Activity() {
    var helper: DevicePolicyHelper? = null
    var lockSwitch: Switch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        helper = DevicePolicyHelper(this)

        lockSwitch = findViewById<Switch?>(R.id.lockSwitch)

        // set current state
        lockSwitch!!.setChecked(helper!!.isUninstallBlocked())

        lockSwitch!!.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                helper!!.blockUninstall()
            } else {
                helper!!.allowUninstall()
            }
        })
    }
}