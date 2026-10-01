package com.tempo.instruments.database

import androidx.room.Entity

@Entity(tableName = "machine_readings", primaryKeys = ["machineId", "timestamp"])
data class MachineReadingEntity(
    val machineId: String,
    val timestamp: Long,
    val temperature: Double,
    val pressure: Double
)