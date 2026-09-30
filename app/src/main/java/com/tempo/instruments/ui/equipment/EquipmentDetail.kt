package com.tempo.instruments.ui.equipment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tempo.instruments.data.Machine
import com.tempo.instruments.data.MachineReading
import com.tempo.instruments.ui.dashboard.MachineStatus
@Composable
fun EquipmentDetailScreen(machine: Machine, readings: List<MachineReading>, modifier: Modifier = Modifier){
    val scrollState = rememberScrollState()

    Column(modifier = modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp)) {
        Text(text = machine.name, style = MaterialTheme.typography.headlineSmall)
        Text(text = "Machine ID: ${machine.id}", style = MaterialTheme.typography.bodyMedium)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Status", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                MachineStatus(isOnline = machine.isOnline)
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Current Conditions", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Temperature: ${machine.temperature} °C")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Pressure: ${machine.pressure} bar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier=Modifier.fillMaxWidth()){
            Column(modifier= Modifier.padding(16.dp)){
                Text(text="Temperature History", style= MaterialTheme.typography.titleMedium)
                Spacer(modifier= Modifier.height(16.dp))
                TemperatureChart(readings=readings)
            }
        }
    }
}