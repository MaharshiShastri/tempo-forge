package com.tempo.instruments.database

import com.tempo.instruments.data.ErrorSeverity

object TempoDatabaseSeeder {

    fun machines(): List<MachineEntity> {

        return listOf(

            MachineEntity(
                id = "SC-001",
                name = "Stability Chamber 01",
                temperature = 25.4,
                pressure = 1.2,
                isOnline = true
            ),

            MachineEntity(
                id = "DF-001",
                name = "Deep Freezer 01",
                temperature = -78.2,
                pressure = 1.0,
                isOnline = true
            ),

            MachineEntity(
                id = "OV-001",
                name = "Hot Air Oven 01",
                temperature = 57.0,
                pressure = 1.2,
                isOnline = false
            )
        )
    }

    fun readings(): List<MachineReadingEntity> {

        return listOf(

            MachineReadingEntity(
                machineId = "SC-001",
                timestamp = 1,
                temperature = 24.8,
                pressure = 1.2
            ),

            MachineReadingEntity(
                machineId = "SC-001",
                timestamp = 2,
                temperature = 25.0,
                pressure = 1.2
            ),

            MachineReadingEntity(
                machineId = "SC-001",
                timestamp = 3,
                temperature = 25.2,
                pressure = 1.2
            ),

            MachineReadingEntity(
                machineId = "SC-001",
                timestamp = 4,
                temperature = 25.4,
                pressure = 1.2
            ),

            MachineReadingEntity(
                machineId = "SC-001",
                timestamp = 5,
                temperature = 25.3,
                pressure = 1.2
            ),

            MachineReadingEntity(
                machineId = "DF-001",
                timestamp = 1,
                temperature = -77.8,
                pressure = 1.0
            ),

            MachineReadingEntity(
                machineId = "DF-001",
                timestamp = 2,
                temperature = -78.0,
                pressure = 1.0
            ),

            MachineReadingEntity(
                machineId = "DF-001",
                timestamp = 3,
                temperature = -78.2,
                pressure = 1.0
            ),

            MachineReadingEntity(
                machineId = "DF-001",
                timestamp = 4,
                temperature = -78.1,
                pressure = 1.0
            ),

            MachineReadingEntity(
                machineId = "DF-001",
                timestamp = 5,
                temperature = -78.2,
                pressure = 1.0
            )
        )
    }

    fun errors(): List<ErrorEventEntity> {

        return listOf(

            ErrorEventEntity(
                id = "ERR-01",
                machineId = "SC-001",
                code = "TEMP_HIGH",
                title = "Temperature Above Setpoint",
                description = "Temperature exceeded the configured upper limit.",
                timestamp = 1,
                severity = ErrorSeverity.WARNING,
                acknowledged = false
            ),

            ErrorEventEntity(
                id = "ERR-02",
                machineId = "OV-001",
                code = "SENSOR_FAILURE",
                title = "Temperature Sensor Failure",
                description = "The temperature sensor is not returning valid readings.",
                timestamp = 2,
                severity = ErrorSeverity.CRITICAL,
                acknowledged = false
            )
        )
    }
}