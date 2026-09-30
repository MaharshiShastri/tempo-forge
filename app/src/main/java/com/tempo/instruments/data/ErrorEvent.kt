package com.tempo.instruments.data

data class ErrorEvent(
    val id: String,
    val machineId: String,
    val code: String,
    val title: String,
    val description: String,
    val timestamp: Long,
    val severity: ErrorSeverity,
    val acknowledged: Boolean
)

enum class ErrorSeverity{INFO, WARNING, CRITICAL}