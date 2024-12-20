package com.example.phisguardandroidapp

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val startScanButton :Button= findViewById(R.id.start_scan_button)
        startScanButton.setOnClickListener{
            val intent = Intent(this@MainActivity,HomeScreenActivity::class.java)
            startActivity(intent)

        }
    }
}