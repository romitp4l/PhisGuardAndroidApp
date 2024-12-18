package com.example.phisguardandroidapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.phisguardandroidapp.AboutUsActivty
import com.example.phisguardandroidapp.FetchMessagesActivity
import com.example.phisguardandroidapp.R
import com.example.phisguardandroidapp.ResultDashboardScreenActivity

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var urlInput: EditText
    private lateinit var sendUrlButton: Button
    private lateinit var messagesButton: Button
    private lateinit var aboutUsButton: Button
    private lateinit var clearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        // Initialize views
        urlInput = findViewById(R.id.urlInput)
        sendUrlButton = findViewById(R.id.sendUrlButton)
        messagesButton = findViewById(R.id.messagesButton)
        aboutUsButton = findViewById(R.id.aboutUsButton)
        clearButton = findViewById(R.id.clearButton)

        // Set click listeners
        sendUrlButton.setOnClickListener {
            val url = urlInput.text.toString().trim()
            if (url.isEmpty()) {
                Toast.makeText(this, "Please enter a valid URL", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, ResultDashboardScreenActivity::class.java).apply {
                    putExtra("url", url)
                }
                startActivity(intent)
            }
        }

        messagesButton.setOnClickListener {
            val intent = Intent(this, FetchMessagesActivity::class.java)
            startActivity(intent)
        }

        aboutUsButton.setOnClickListener {
            val intent = Intent(this, AboutUsActivty::class.java)
            startActivity(intent)
        }

        clearButton.setOnClickListener {
            urlInput.text.clear()
        }
    }
}
