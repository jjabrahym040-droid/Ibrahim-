package com.androidify.smoke

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val label = TextView(this)
        label.textSize = 18f
        label.setPadding(48, 96, 48, 48)
        label.text = "Androidify Smoke\n" + ApiConfig.BASE_URL
        setContentView(label)
    }
}
