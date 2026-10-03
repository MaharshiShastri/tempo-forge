package com.tempo.instruments.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun WifiConfigurationScreen(onSave: (String, String)->Unit){
    var ssid by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(modifier=Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Network Configuration", style = MaterialTheme.typography.headlineSmall)
        Text(text = "Configure the network used to communicate with Tempo gateway.", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(value=ssid, onValueChange = {ssid=it}, modifier= Modifier.fillMaxWidth(), label={Text("Wi-Fi name")}, singleLine = true)
        OutlinedTextField(value=password, onValueChange = {password=it}, modifier= Modifier.fillMaxWidth(), label={Text("Wi-Fi password")}, visualTransformation = PasswordVisualTransformation(), singleLine = true)
        Button(onClick = {onSave(ssid, password)}, modifier = Modifier.fillMaxWidth(), enabled = ssid.isNotBlank() && password.isNotBlank()){
            Text("Save Network")
        }
    }
}