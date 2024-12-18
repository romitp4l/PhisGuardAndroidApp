package com.example.phisguardandroidapp



import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class UrlRequest(val url: String)
data class ApiResponse(
    val url: String?,
    val ip_address: String?,
    val dns_a_records: List<String>?,
    val dns_records_error: String?,
    val domain: String?,
    val double_slash_redirect: Boolean?,
    val external_links: Int?,
    val favicon: Boolean?,
    val hasAt: Boolean?, // You can rename this field to something like hasAt or similar
    val https: Boolean?,
    val iframes: Int?,
    val login_form: Boolean?,
    val netloc: String?,
    val path: String?,
    val phishing_score: Int?,
    val query: String?,
    val scheme: String?,
    val scripts: Int?,
    val shortened: Boolean?,
    val subdomain: String?,
    val suffix: String?,
    val title: String?,
    val whois_error: String?
)



interface ApiService {
    @POST("analyze")
    suspend fun analyzeUrl(@Body urlRequest: UrlRequest): Response<ApiResponse>
}
