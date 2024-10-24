package com.example.cam17

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

open class BaseActivity : login() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    protected fun setMainContent(layoutResID: Int) {
        val frameLayout = findViewById<FrameLayout>(R.id.activity_content)
        layoutInflater.inflate(layoutResID, frameLayout, true)
    }
}

