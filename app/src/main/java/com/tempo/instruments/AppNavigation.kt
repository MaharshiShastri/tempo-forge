package com.tempo.instruments

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tempo.instruments.data.Machine
import com.tempo.instruments.ui.dashboard.DashboardScreen
import com.tempo.instruments.ui.equipment.EquipmentDetailScreen
import com.tempo.instruments.data.MachineReading

@Composable
fun AppNavigation(
    machines: List<Machine>,
    readings: Map<String, List<MachineReading>>
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(machines = machines, onMachineClick = { machine ->
                    navController.navigate("equipment/${machine.id}")
                }
            )
        }

        composable(route = "equipment/{machineId}",
            arguments = listOf(navArgument("machineId") {type = NavType.StringType})
        ) { backStackEntry ->

            val machineId = backStackEntry.arguments?.getString("machineId")

            val machine = machines.find { it.id == machineId }
            val machineReadings = readings[machineId] ?: emptyList()
            if (machine != null) {
                EquipmentDetailScreen(machine = machine, readings=machineReadings)
            }
        }
    }
}