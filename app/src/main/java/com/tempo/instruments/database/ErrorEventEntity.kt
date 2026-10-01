package com.tempo.instruments.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tempo.instruments.data.ErrorSeverity

@Entity(tableName = "error_events")
data class ErrorEventEntity(
    @PrimaryKey
    val id: String,
    val machineId: String,
    val code: String,
    val title: String,
    val description: String,
    val timestamp: Long,
    val severity: ErrorSeverity,
    val acknowledged: Boolean
)