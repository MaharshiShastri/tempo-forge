package com.tempo.instruments.database

import androidx.room.Entity

@Entity(tableName = "network_configuration", primaryKeys = ["id"])
data class NetworkConfigurationEntity(
    val id: Int=1,
    val ssid: String,
    val password: String
)