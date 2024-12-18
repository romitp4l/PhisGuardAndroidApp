package com.example.phisguardandroidapp

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultDashboardScreenActivity : AppCompatActivity() {

    private lateinit var urlTextView: TextView
    private lateinit var ipAddressTextView: TextView
    private lateinit var dnsRecordsTextView: TextView
    private lateinit var dnsErrorTextView: TextView
    private lateinit var phishingScoreTextView: TextView
    private lateinit var otherDetailsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_dashboard_screen)

        // Initialize TextViews
        urlTextView = findViewById(R.id.urlText)
        ipAddressTextView = findViewById(R.id.ipAddressText)
        dnsRecordsTextView = findViewById(R.id.dnsRecordsText)
        dnsErrorTextView = findViewById(R.id.dnsErrorText)
        phishingScoreTextView = findViewById(R.id.phishingScoreText)
        otherDetailsTextView = findViewById(R.id.otherDetailsText)

        // Get URL from Intent
        val url = intent.getStringExtra("url")
        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "No URL received", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            // Send URL to the API
            analyzeUrl(url)
        }
    }

    private fun analyzeUrl(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.apiService.analyzeUrl(UrlRequest(url))
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        withContext(Dispatchers.Main) {
                            displayResult(apiResponse)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            showError("Empty response from server")
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showError("API call failed: HTTP ${response.code()} - ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("Network error: ${e.localizedMessage ?: "Unknown error"}")
                }
            }
        }
    }

    private fun displayResult(apiResponse: ApiResponse) {
        urlTextView.text = "URL: ${apiResponse.url ?: "N/A"}"
        ipAddressTextView.text = "IP Address: ${apiResponse.ip_address ?: "N/A"}"
        dnsRecordsTextView.text = "DNS A Records: ${apiResponse.dns_a_records?.joinToString(", ") ?: "N/A"}"
        dnsErrorTextView.text = "DNS Error: ${apiResponse.dns_records_error ?: "N/A"}"
        phishingScoreTextView.text = "Phishing Score: ${apiResponse.phishing_score ?: "N/A"}"
        // You can highlight or style phishing score if necessary
        phishingScoreTextView.setTextColor(if (apiResponse.phishing_score?.toInt() ?: 0 > 70) {
            Color.RED
        } else {
            Color.GREEN
        })

        // Update other details dynamically
        otherDetailsTextView.text = buildString {
            append("Domain: ${apiResponse.domain ?: "N/A"}\n")
            append("Subdomain: ${apiResponse.subdomain ?: "N/A"}\n")
            append("Scheme: ${apiResponse.scheme ?: "N/A"}\n")
            append("Path: ${apiResponse.path ?: "N/A"}\n")
            append("Query: ${apiResponse.query ?: "N/A"}\n")
            append("Login Form: ${apiResponse.login_form ?: "N/A"}\n")
            append("External Links: ${apiResponse.external_links ?: "N/A"}\n")
            append("Favicon: ${apiResponse.favicon ?: "N/A"}\n")
            append("HTTPS: ${apiResponse.https ?: "N/A"}\n")
            append("Iframes: ${apiResponse.iframes ?: "N/A"}\n")
            append("Scripts: ${apiResponse.scripts ?: "N/A"}\n")
            append("Shortened URL: ${apiResponse.shortened ?: "N/A"}\n")
            append("Title: ${apiResponse.title?.trim() ?: "N/A"}\n")
            append("Whois Error: ${apiResponse.whois_error ?: "N/A"}\n")
            append("Double Slash Redirect: ${apiResponse.double_slash_redirect ?: "N/A"}\n")
            append("Has '@' in URL: ${apiResponse.hasAt ?: "N/A"}")
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

