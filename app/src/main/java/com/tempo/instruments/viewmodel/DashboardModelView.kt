package com.tempo.instruments.viewmodel

import androidx.lifecycle.ViewModel
import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.ErrorSeverity
import com.tempo.instruments.data.Machine
import com.tempo.instruments.data.MachineReading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardModelView : ViewModel() {
    private val _machines = MutableStateFlow(
        listOf(
            Machine(
                id = "SC-001",
                name = "Stability Chamber 01",
                temperature = 25.4,
                pressure = 1.2,
                isOnline = true
            ),
            Machine(
                id = "DF-001",
                name = "Deep Freezer 01",
                temperature = -78.2,
                pressure = 1.0,
                isOnline = true
            ),
            Machine(
                id = "OV-001",
                name = "Hot Air Oven 01",
                temperature = 57.0,
                pressure = 1.2,
                isOnline = false
            )
        )
    )

    private val _readings = MutableStateFlow(
        mapOf(
            "SC-001" to listOf(
                MachineReading(1, 24.8, 1.2),
                MachineReading(2, 25.0, 1.2),
                MachineReading(3, 25.2, 1.2),
                MachineReading(4, 25.4, 1.2),
                MachineReading(5, 25.3, 1.2)
            ),
            "DF-001" to listOf(
                MachineReading(1, -77.8, 1.0),
                MachineReading(2, -78.0, 1.0),
                MachineReading(3, -78.2, 1.0),
                MachineReading(4, -78.1, 1.0),
                MachineReading(5, -78.2, 1.0)
            ),
            "OV-001" to listOf(
                MachineReading(1, 54.0, 1.2),
                MachineReading(2, 55.5, 1.2),
                MachineReading(3, 56.2, 1.2),
                MachineReading(4, 57.0, 1.2),
                MachineReading(5, 57.0, 1.2)
            )
        )
    )

    private val _errors = MutableStateFlow(
        listOf(
            ErrorEvent(id="ERR-01", machineId="SC-001", code="TEMP_HIGH",
                title="Temperature Above Setpoint", description="Temperature exceeded the configured, upper limit",
                timestamp = 1, severity = ErrorSeverity.WARNING, acknowledged = false
            ),
            ErrorEvent(id="ERR-02", machineId="OV-001", code="SENSOR_FAILURE", title="Temperature Sensor Failure",
                description="The temperature sensor is not responding valid readings.",
                timestamp = 2, severity = ErrorSeverity.CRITICAL, acknowledged = false)
        )
    )

    val errors: StateFlow<List<ErrorEvent>> = _errors.asStateFlow()
    val machines: StateFlow<List<Machine>> = _machines.asStateFlow()
    val readings: StateFlow<Map<String, List<MachineReading>>> = _readings.asStateFlow()
}
