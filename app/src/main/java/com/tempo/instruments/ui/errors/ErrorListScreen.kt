package com.tempo.instruments.ui.errors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
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
import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.ErrorSeverity

@Composable
fun ErrorListScreen(errors: List<ErrorEvent>, onErrorClick: (ErrorEvent) -> Unit, modifier: Modifier = Modifier){
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        Text(text="Errors & Alerts", style=MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        if(errors.isEmpty()){
            Text(text="No active errors or alerts,", style= MaterialTheme.typography.bodyLarge, color= MaterialTheme.colorScheme.onSurfaceVariant)
        }else{
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items = errors, key = { it.id }) { error ->
                    ErrorCard(error = error, onClick = { onErrorClick(error) })
                }
            }
        }
    }
}

@Composable
fun ErrorCard(error: ErrorEvent, onClick: () -> Unit){
    Card(modifier=Modifier.fillMaxWidth().clickable(onClick=onClick)) {
        Column(modifier=Modifier.padding(16.dp)){
            Row(modifier=Modifier.fillMaxWidth()){
                Column(modifier=Modifier.weight(1f)){
                    Text(text=error.title, style=MaterialTheme.typography.titleMedium)
                    Spacer(modifier=Modifier.height(4.dp))
                    Text(text=error.code, style=MaterialTheme.typography.labelMedium, color= MaterialTheme.colorScheme.onSurfaceVariant)
                }
                SeverityBadge(severity=error.severity)
            }
            Spacer(modifier= Modifier.height(12.dp))
            Text(text=error.description, style=MaterialTheme.typography.bodyMedium)
            Spacer(modifier=Modifier.height(12.dp))
            Text(text = if(error.acknowledged){"Acknowledged"}else{"Action Required"}, style=MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun SeverityBadge(severity: ErrorSeverity){
    val containerColor = when(severity){
        ErrorSeverity.INFO -> MaterialTheme.colorScheme.secondaryContainer
        ErrorSeverity.WARNING -> MaterialTheme.colorScheme.tertiaryContainer
        ErrorSeverity.CRITICAL -> MaterialTheme.colorScheme.errorContainer
    }

    val contentColor = when(severity){
        ErrorSeverity.INFO -> MaterialTheme.colorScheme.onSecondaryContainer
        ErrorSeverity.WARNING -> MaterialTheme.colorScheme.onTertiaryContainer
        ErrorSeverity.CRITICAL -> MaterialTheme.colorScheme.onErrorContainer
    }

    Surface(color=containerColor, shape=MaterialTheme.shapes.small){
        Text(
            text=severity.name, color=contentColor, style=MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal=10.dp, vertical=6.dp)
        )
    }
}