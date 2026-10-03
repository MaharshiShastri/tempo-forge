package com.tempo.instruments.repository

import android.util.Log
import com.tempo.instruments.data.NetworkTelemetry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class NetworkTempoDataSource {

    private val client = OkHttpClient()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun fetchTelemetry(
        baseUrl: String
    ): NetworkTelemetry = withContext(Dispatchers.IO) {

        val url = "$baseUrl/telemetry"

        Log.d("TELEMETRY", "Requesting: $url")

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->

            Log.d("TELEMETRY", "HTTP status: ${response.code}")

            if (!response.isSuccessful) {
                throw Exception(
                    "Telemetry request failed: ${response.code}"
                )
            }

            val body = response.body?.string()
                ?: throw Exception("Empty telemetry response")

            Log.d("TELEMETRY", "Raw JSON: $body")

            val telemetry = json.decodeFromString<NetworkTelemetry>(body)

            Log.d("TELEMETRY", "Parsed telemetry: $telemetry")

            telemetry
        }
    }
}