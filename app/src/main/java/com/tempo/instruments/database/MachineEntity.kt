package com.tempo.instruments.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "machines")
data class MachineEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val temperature: Double,
    val pressure: Double,
    val isOnline: Boolean
)