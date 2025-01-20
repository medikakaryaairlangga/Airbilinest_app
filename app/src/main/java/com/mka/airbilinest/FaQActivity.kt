package com.mka.airbilinest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge

class FaQActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fa_qactivity)
    }
}
