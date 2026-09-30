package com.tempo.instruments.data

data class Machine(
    val id: String,
    val name: String,
    val temperature: Double,
    val pressure: Double,
    val isOnline: Boolean
)