package com.tempo.instruments.ui.errors

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ServiceCallButton(phoneNumber: String){
    val context = LocalContext.current

    Button(onClick = {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+91$phoneNumber"))
    context.startActivity(intent)}){
        Text(text="Call Tempo Service")
    }
}