package com.tempo.instruments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tempo.instruments.data.Machine
import com.tempo.instruments.ui.dashboard.DashboardScreen
import com.tempo.instruments.ui.equipment.EquipmentDetailScreen
import com.tempo.instruments.ui.errors.ErrorDetailScreen
import com.tempo.instruments.ui.errors.ErrorListScreen
import com.tempo.instruments.ui.settings.WifiConfigurationScreen
import com.tempo.instruments.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    machines: List<Machine>,
    errors: List<com.tempo.instruments.data.ErrorEvent>,
    dashboardViewModel: DashboardViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Tempo Instruments")
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("network")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Network Settings"
                        )
                    }
                    Button(onClick = {dashboardViewModel.fetchTelemetry("")}) {Text("Receive Telemetry") }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = "dashboard"
            ) {

                composable("dashboard") {
                    DashboardScreen(
                        machines = machines,
                        onMachineClick = { machine ->
                            navController.navigate("equipment/${machine.id}")
                        },
                        onErrorsClick = {
                            navController.navigate("errors")
                        }
                    )
                }

                composable(
                    route = "equipment/{machineId}",
                    arguments = listOf(
                        navArgument("machineId") {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->

                    val machineId =
                        backStackEntry.arguments?.getString("machineId")

                    val machine = machines.find {
                        it.id == machineId
                    }

                    if (machine != null && machineId != null) {
                        EquipmentDetailScreen(
                            machine = machine,
                            readings = dashboardViewModel
                                .observeReadings(machineId)
                        )
                    }
                }

                composable("errors") {
                    ErrorListScreen(
                        errors = errors,
                        onErrorClick = { error ->
                            navController.navigate("error/${error.id}")
                        }
                    )
                }

                composable(
                    route = "error/{errorId}",
                    arguments = listOf(
                        navArgument("errorId") {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->

                    val errorId =
                        backStackEntry.arguments?.getString("errorId")

                    val error = errors.find {
                        it.id == errorId
                    }

                    if (error != null) {
                        val machine = machines.find {
                            it.id == error.machineId
                        }

                        ErrorDetailScreen(
                            error = error,
                            machineName = machine?.name,
                            onAcknowledge = {
                                dashboardViewModel.acknowledgeError(error.id)
                            }
                        )
                    }
                }

                composable("network") {
                    WifiConfigurationScreen(
                        onSave = { ssid, password ->
                            dashboardViewModel.saveWifiConfiguration(
                                ssid = ssid,
                                password = password
                            )

                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}