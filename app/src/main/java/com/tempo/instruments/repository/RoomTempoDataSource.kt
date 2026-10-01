package com.tempo.instruments.repository

import com.tempo.instruments.database.ErrorEventDao
import com.tempo.instruments.database.MachineDao
import com.tempo.instruments.database.MachineReadingDao
import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.Machine
import com.tempo.instruments.data.MachineReading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomTempoDataSource(
    private val machineDao: MachineDao,
    private val readingDao: MachineReadingDao,
    private val errorDao: ErrorEventDao
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
}