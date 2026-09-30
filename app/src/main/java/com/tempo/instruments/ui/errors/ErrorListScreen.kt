package com.tempo.instruments.ui.errors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.ErrorSeverity

@Composable
fun ErrorListScreen(errors: List<ErrorEvent>, onErrorClick: (ErrorEvent) -> Unit){
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        Text(text="Errors & Alerts", style=MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items=errors, key={it.id}){ error ->
                ErrorCard(error=error, onClick={onErrorClick(error)})
            }
        }
    }
}

@Composable
fun ErrorCard(error: ErrorEvent, onClick: () -> Unit){
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick=onClick)){
        Column(modifier= Modifier.padding(16.dp)){
            Text(text=error.title, style=MaterialTheme.typography.titleMedium)
            Spacer(modifier=Modifier.height(4.dp))
            Text(text=error.code, style= MaterialTheme.typography.labelMedium)
            Spacer(modifier=Modifier.height(8.dp))
            Text(text=error.severity.name, style=MaterialTheme.typography.labelMedium)
        }
    }
}