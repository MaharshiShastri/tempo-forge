package com.tempo.instruments.data

data class MachineReading(
    val timestamp: Long,
    val temperature: Double,
    val pressure: Double
)