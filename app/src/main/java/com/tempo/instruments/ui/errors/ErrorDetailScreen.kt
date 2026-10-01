package com.tempo.instruments.ui.errors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.ErrorSeverity

@Composable
fun ErrorDetailScreen(error: ErrorEvent, machineName: String?, onAcknowledge: () -> Unit, modifier: Modifier = Modifier){
    Column(
        modifier=modifier.fillMaxSize().verticalScroll(
            rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text=error.title, style= MaterialTheme.typography.headlineSmall)
        Text(text=error.code, style= MaterialTheme.typography.bodyMedium, color= MaterialTheme.colorScheme.onSurfaceVariant)
        Card(modifier=Modifier.fillMaxWidth()){
            Column(modifier=Modifier.padding(16.dp)){
                Text(
                    text="Severity",
                    style=MaterialTheme.typography.labelMedium,
                    color=MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier=Modifier.height(6.dp))
                SeverityBadge(severity = error.severity)
            }
        }

        Card(modifier=Modifier.fillMaxWidth()){
            Column(modifier=Modifier.padding(16.dp)){
                Text(text="Equipment", style= MaterialTheme.typography.labelMedium, color= MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier=Modifier.height(6.dp))
                Text(text= machineName?:error.machineId, style= MaterialTheme.typography.titleMedium)
                Text(text="Machine ID: ${error.machineId}", style= MaterialTheme.typography.bodySmall)
            }
        }

        Card(modifier=Modifier.fillMaxWidth()){
            Column(modifier=Modifier.padding(16.dp)){
                Text(text="What happened?", style=MaterialTheme.typography.titleMedium)
                Spacer(modifier=Modifier.height(8.dp))
                Text(text=error.description, style= MaterialTheme.typography.bodyLarge)
                if(error.severity == ErrorSeverity.CRITICAL){
                    Card(modifier= Modifier.fillMaxWidth()){
                        Column(modifier= Modifier.padding(16.dp)) {
                            Text(
                                text="Immediate Service Required",
                                style= MaterialTheme.typography.titleMedium,
                                color= MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier=Modifier.height(8.dp))
                            Text(
                                text="Please call our Tempo Service team immediately!" +
                                        "The error can only be resolved by the service team.",
                                style= MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier=Modifier.height(12.dp))
                            ServiceCallButton(phoneNumber = "8080512285")
                        }
                    }
                }
            }
        }

        Card(modifier=Modifier.fillMaxWidth()){
            Column(modifier=Modifier.padding(16.dp)){
                Text(text="Engineering Guidance", style=MaterialTheme.typography.titleMedium)
                Spacer(modifier=Modifier.height(8.dp))
                Text(
                    text="Detailed troubleshooting guidance will appear here.",
                    style=MaterialTheme.typography.bodyMedium,
                    color= MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if(!error.acknowledged){
            Button(onClick=onAcknowledge, modifier=Modifier.fillMaxWidth()) {
                Text("Acknowledge Error")
            }
        }
        else{
            Text(
                text="This error has been acknowledged.",
                style=MaterialTheme.typography.bodyMedium,
                color= MaterialTheme.colorScheme.onSurfaceVariant
                )
        }
    }
}