package com.tempo.instruments.repository

import com.tempo.instruments.database.ErrorEventDao
import com.tempo.instruments.database.MachineDao
import com.tempo.instruments.database.MachineReadingDao
import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.Machine
import com.tempo.instruments.data.MachineReading
import com.tempo.instruments.data.WifiConfiguration
import com.tempo.instruments.database.MachineReadingEntity
import com.tempo.instruments.database.NetworkConfigurationDao
import com.tempo.instruments.database.NetworkConfigurationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.util.Log

class RoomTempoDataSource(
    private val machineDao: MachineDao,
    private val readingDao: MachineReadingDao,
    private val errorDao: ErrorEventDao,
    private val networkConfigurationDao: NetworkConfigurationDao
) : TempoDataSource {

    override fun observeMachines(): Flow<List<Machine>> =
        machineDao.observeMachines().map { entities ->

            entities.map { entity ->

                Machine(
                    id = entity.id,
                    name = entity.name,
                    temperature = entity.temperature,
                    pressure = entity.pressure,
                    isOnline = entity.isOnline
                )
            }
        }

    override fun observeReadings(
        machineId: String
    ): Flow<List<MachineReading>> =

        readingDao.observeReadings(machineId).map { entities ->

            entities.map { entity ->

                MachineReading(
                    timestamp = entity.timestamp,
                    temperature = entity.temperature,
                    pressure = entity.pressure
                )
            }
        }

    override fun observeErrors(): Flow<List<ErrorEvent>> =

        errorDao.observeErrors().map { entities ->

            entities.map { entity ->

                ErrorEvent(
                    id = entity.id,
                    machineId = entity.machineId,
                    code = entity.code,
                    title = entity.title,
                    description = entity.description,
                    timestamp = entity.timestamp,
                    severity = entity.severity,
                    acknowledged = entity.acknowledged
                )
            }
        }

    override suspend fun acknowledgeError(
        errorId: String
    ) {
        errorDao.acknowledgeError(errorId)
    }

    override fun observeWifiConfiguration(): Flow<WifiConfiguration?> =
        networkConfigurationDao.observeConfiguration().map { entity ->
            entity?.let{
                WifiConfiguration(ssid = it.ssid, password = it.password)
        }
    }

    override suspend fun saveWifiConfiguration(configuration: WifiConfiguration) {
        networkConfigurationDao.saveConfiguration(NetworkConfigurationEntity(id=1, ssid=configuration.ssid, password = configuration.password))
    }

    override suspend fun updateMachineTelemetry(
        machineId: String,
        temperature: Double,
        pressure: Double,
        isOnline: Boolean
    ) {
        val updatedRows = machineDao.updateTelemetry(machineId = machineId, temperature = temperature, pressure = pressure, isOnline = isOnline)
        Log.d("TELEMETRY", "Updated $updatedRows machine(s)")
    }

    override suspend fun insertReading(machineId: String, reading: MachineReading){
        readingDao.insertReading(MachineReadingEntity(machineId = machineId, timestamp = reading.timestamp, temperature = reading.temperature, pressure = reading.pressure))
    }
}