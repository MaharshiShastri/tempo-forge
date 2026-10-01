package com.tempo.instruments

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.tempo.instruments.ui.theme.TempoInstrumentsTheme
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tempo.instruments.viewmodel.DashboardViewModelFactory
import com.tempo.instruments.viewmodel.DashboardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{ TempoInstrumentsTheme{TempoApp()}}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempoApp() {

    val application = androidx.compose.ui.platform.LocalContext.current
        .applicationContext as TempoApplication

    val dashboardViewModel: DashboardViewModel = viewModel(
        factory = DashboardViewModelFactory(
            application.appContainer.repository
        )
    )

    val machines by dashboardViewModel.machines.collectAsState()
    val errors by dashboardViewModel.errors.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Tempo Instruments")
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            AppNavigation(
                machines = machines,
                errors = errors,
                dashboardViewModel = dashboardViewModel
            )
        }
    }
}

