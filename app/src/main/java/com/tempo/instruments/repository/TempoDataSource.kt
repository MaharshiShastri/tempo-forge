package com.tempo.instruments.repository

import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.Machine
import com.tempo.instruments.data.MachineReading
import kotlinx.coroutines.flow.Flow

interface TempoDataSource{
    fun observeMachines(): Flow<List<Machine>>
    fun observeReadings(machineId: String): Flow<List<MachineReading>>
    fun observeErrors(): Flow<List<ErrorEvent>>
    suspend fun acknowledgeError(errorId: String)
}