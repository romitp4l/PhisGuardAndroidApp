package com.example.phisguardandroidapp



import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.database.Cursor
import android.net.Uri

import android.text.TextUtils
import android.widget.Button

import androidx.activity.result.contract.ActivityResultContracts.RequestPermission

import java.util.regex.Matcher
import java.util.regex.Pattern

class FetchMessagesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnFetchMessages: Button
    private lateinit var messageAdapter: MessageAdapter
    private val urlList = ArrayList<String>()

    // For requesting permission
    private val permissionLauncher = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            fetchMessages()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_messages)

        recyclerView = findViewById(R.id.recyclerView)
        btnFetchMessages = findViewById(R.id.btnFetchMessages)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Pass context to the adapter
        messageAdapter = MessageAdapter(this, urlList)
        recyclerView.adapter = messageAdapter

        btnFetchMessages.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                fetchMessages()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_SMS)
            }
        }
    }

    // Fetch SMS messages and extract URLs
    private fun fetchMessages() {
        urlList.clear()
        val uri: Uri = Telephony.Sms.CONTENT_URI
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)

        cursor?.let {
            if (it.moveToFirst()) {
                val indexBody = it.getColumnIndex(Telephony.Sms.BODY)
                do {
                    val message = it.getString(indexBody)
                    extractUrls(message)
                } while (it.moveToNext())
            }
            it.close()
        }

        messageAdapter.notifyDataSetChanged()
    }

    // Extract URLs from the message body
    private fun extractUrls(message: String) {
        if (!TextUtils.isEmpty(message)) {
            val pattern: Pattern = Pattern.compile("https?://[^\\s]+")
            val matcher: Matcher = pattern.matcher(message)
            while (matcher.find()) {
                val url = matcher.group()
                urlList.add(url)
            }
        }
    }
}
