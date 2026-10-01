package com.tempo.instruments.ui.dashboard
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tempo.instruments.data.Machine
import androidx.compose.material3.Button

@Composable
fun DashboardScreen(
    machines: List<Machine>,
    onMachineClick: (Machine) -> Unit,
    onErrorsClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.fillMaxSize().padding(16.dp)){
        Text(text="Your Equipment", style= MaterialTheme.typography.headlineSmall)
        Spacer(modifier=Modifier.height(16.dp))
        Button(onClick = onErrorsClick, modifier = Modifier.fillMaxWidth()){Text("Errors & Alerts")}
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items=machines, key={machine -> machine.id}){
                machine ->
                EquipmentCard(machine, onClick = { onMachineClick(machine) })
            }
        }
    }
}

@Composable
fun EquipmentCard(machine: Machine, onClick: () -> Unit){
    Card(
        modifier=Modifier.fillMaxWidth().clickable(onClick=onClick),
        border= BorderStroke(width=1.dp, color= MaterialTheme.colorScheme.outline)
        ){
        Column(modifier=Modifier.padding(16.dp)){
            Text(text=machine.name, style=MaterialTheme.typography.titleLarge)
            Spacer(modifier=Modifier.height(4.dp))
            Text(text=machine.id, style=MaterialTheme.typography.bodySmall, color=MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier=Modifier.height(8.dp))
            MachineStatus(isOnline=machine.isOnline)
            Spacer(modifier=Modifier.height(16.dp))
            Text(text="Temperature", style=MaterialTheme.typography.labelMedium, color= MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text="${machine.temperature} °C", style=MaterialTheme.typography.titleMedium)
            Spacer(modifier=Modifier.height(8.dp))
            Text(text="Pressure", style=MaterialTheme.typography.labelMedium, color=MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text="${machine.pressure} bar", style=MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun MachineStatus(isOnline: Boolean){
    Surface(
        shape = MaterialTheme.shapes.small,
        color = if(isOnline){MaterialTheme.colorScheme.primaryContainer} else{MaterialTheme.colorScheme.errorContainer}
    ){
        Text(
            text=if(isOnline){"●  ONLINE"} else{"●  OFFLINE"},
            modifier=Modifier.padding(horizontal = 10.dp, vertical=6.dp),
            color=if(isOnline){MaterialTheme.colorScheme.onPrimaryContainer} else{MaterialTheme.colorScheme.onErrorContainer},
            style=MaterialTheme.typography.labelMedium
        )
    }
}