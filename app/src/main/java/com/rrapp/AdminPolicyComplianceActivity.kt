package com.rrapp

import android.app.Activity
import android.os.Bundle

class AdminPolicyComplianceActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setResult(RESULT_OK)
        finish()
    }
}