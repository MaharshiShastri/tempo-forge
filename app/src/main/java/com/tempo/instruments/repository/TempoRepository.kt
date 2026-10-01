package com.tempo.instruments.repository

import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.Machine
import com.tempo.instruments.data.MachineReading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TempoRepository(
    private val dataSource: TempoDataSource
) : TempoDataSource {

    override fun observeMachines(): Flow<List<Machine>> = dataSource.observeMachines()

    override fun observeReadings(machineId: String): Flow<List<MachineReading>> = dataSource.observeReadings(machineId)

    override fun observeErrors(): Flow<List<ErrorEvent>> = dataSource.observeErrors()


    override suspend fun acknowledgeError(errorId: String) {dataSource.acknowledgeError(errorId)}
}