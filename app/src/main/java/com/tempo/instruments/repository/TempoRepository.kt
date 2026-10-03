package com.tempo.instruments.repository

import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.Machine
import com.tempo.instruments.data.MachineReading
import com.tempo.instruments.data.NetworkTelemetry
import com.tempo.instruments.data.WifiConfiguration
import kotlinx.coroutines.flow.Flow

class TempoRepository(
    private val dataSource: TempoDataSource,
    private val networkTempoDataSource: NetworkTempoDataSource
) : TempoDataSource {

    override fun observeMachines(): Flow<List<Machine>> = dataSource.observeMachines()

    override fun observeReadings(machineId: String): Flow<List<MachineReading>> = dataSource.observeReadings(machineId)

    override fun observeErrors(): Flow<List<ErrorEvent>> = dataSource.observeErrors()

    override suspend fun acknowledgeError(errorId: String) {dataSource.acknowledgeError(errorId)}

    override fun observeWifiConfiguration(): Flow<WifiConfiguration?> = dataSource.observeWifiConfiguration()

    override suspend fun saveWifiConfiguration(configuration: WifiConfiguration) {dataSource.saveWifiConfiguration(configuration)}

    override suspend fun updateMachineTelemetry(
        machineId: String,
        temperature: Double,
        pressure: Double,
        isOnline: Boolean
    ) {
        dataSource.updateMachineTelemetry(machineId = machineId, temperature = temperature, pressure = pressure, isOnline = isOnline)
    }

    override suspend fun insertReading(machineId: String, reading: MachineReading) {
        dataSource.insertReading(machineId = machineId, reading)
    }

    suspend fun fetchAndStoreTelemetry(baseUrl: String): NetworkTelemetry {
        val telemetry = networkTempoDataSource.fetchTelemetry(baseUrl)
        updateMachineTelemetry(
            machineId = telemetry.machineId,
            temperature = telemetry.temperature,
            pressure = telemetry.pressure,
            isOnline = telemetry.isOnline
        )

        insertReading(
            machineId = telemetry.machineId,
            reading = MachineReading(
                timestamp = telemetry.timestamp,
                temperature = telemetry.temperature,
                pressure = telemetry.pressure
            )
        )
        return telemetry
    }
}