package com.androidify.verify

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val label = TextView(this)
        label.textSize = 16f
        label.setPadding(48, 96, 48, 48)
        label.text = "Verify App\n" + ApiConfig.BASE_URL + "\n..."
        setContentView(label)

        // Proves at runtime that the installed app reaches its backend database.
        Thread {
            val result = try {
                ApiClient.get("/api/public/app-ping?app_key=" + ApiConfig.APP_KEY)
            } catch (error: Exception) {
                "offline: " + error.message
            }
            Handler(Looper.getMainLooper()).post {
                label.text = "Verify App\n" + ApiConfig.BASE_URL + "\n" + result
            }
        }.start()
    }
}
