package com.chekrun.roadrunicen

import android.content.Intent
import android.os.Bundle
import android.webkit.CookieManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.chekrun.roadrunicen.util.log

private var counter = 0

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        counter++
        log("Start onCreate: $counter")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        log("StartActivity: onRestoreInstanceState")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        log("StartActivity: onResume")
        super.onResume()
    }

    override fun onPause() {
        log("StartActivity: onPause")
        super.onPause()
    }

}