package com.tempo.instruments.repository

import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.Machine
import com.tempo.instruments.data.MachineReading
import com.tempo.instruments.data.WifiConfiguration
import kotlinx.coroutines.flow.Flow

interface TempoDataSource{
    fun observeMachines(): Flow<List<Machine>>
    fun observeReadings(machineId: String): Flow<List<MachineReading>>
    fun observeErrors(): Flow<List<ErrorEvent>>
    suspend fun acknowledgeError(errorId: String)

    fun observeWifiConfiguration(): Flow<WifiConfiguration?>

    suspend fun saveWifiConfiguration(configuration: WifiConfiguration)

    suspend fun updateMachineTelemetry(machineId: String, temperature: Double, pressure: Double, isOnline: Boolean)

    suspend fun insertReading(machineId: String, reading: MachineReading)


}