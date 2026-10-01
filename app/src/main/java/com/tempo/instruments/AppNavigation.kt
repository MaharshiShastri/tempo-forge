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
import com.tempo.instruments.viewmodel.DashboardViewModel
import com.tempo.instruments.ui.errors.ErrorListScreen
import com.tempo.instruments.ui.errors.ErrorDetailScreen

@Composable
fun AppNavigation(
    machines: List<Machine>,
    errors: List<com.tempo.instruments.data.ErrorEvent>,
    dashboardViewModel: DashboardViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(machines = machines, onMachineClick = { machine ->
                    navController.navigate("equipment/${machine.id}")
                },
                onErrorsClick = {navController.navigate("errors")}
            )
        }

        composable(route = "equipment/{machineId}",
            arguments = listOf(navArgument("machineId") {type = NavType.StringType})
        ) { backStackEntry ->

            val machineId = backStackEntry.arguments?.getString("machineId")

            val machine = machines.find { it.id == machineId }
            if (machine != null && machineId != null) {
                EquipmentDetailScreen(machine = machine, readings=dashboardViewModel.observeReadings(machineId))
            }
        }

        composable(route="errors"){
            ErrorListScreen(errors = errors, onErrorClick = {error ->
                navController.navigate("error/${error.id}")
            })
        }

        composable(route = "error/{errorId}",
            arguments = listOf(navArgument("errorId"){type= NavType.StringType})){backStackEntry ->
            val errorId = backStackEntry.arguments?.getString("errorId")
            val error = errors.find{it.id == errorId}
            if(error != null){
                    val machine = machines.find{it.id == error.machineId}
                    ErrorDetailScreen(error=error, machineName = machine?.name, onAcknowledge = {dashboardViewModel.acknowledgeError(error.id)})
            }
        }
    }
}