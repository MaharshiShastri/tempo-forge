package com.tempo.instruments.data
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTelemetry(
    val machineId: String,
    val temperature: Double,
    val pressure: Double,
    val timestamp: Long,
    val isOnline: Boolean
)