package com.tempo.instruments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tempo.instruments.data.ErrorEvent
import com.tempo.instruments.data.Machine
import com.tempo.instruments.data.MachineReading
import com.tempo.instruments.data.WifiConfiguration
import com.tempo.instruments.repository.TempoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: TempoRepository) : ViewModel() {

    val machines: StateFlow<List<Machine>> = repository.observeMachines().stateIn(
        scope=viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = emptyList()
    )
    val errors: StateFlow<List<ErrorEvent>> = repository.observeErrors().stateIn(
        scope=viewModelScope, started= SharingStarted.WhileSubscribed(5_000), initialValue = emptyList()
    )

    val wifiConfiguration: StateFlow<WifiConfiguration?> = repository.observeWifiConfiguration().stateIn(
        scope = viewModelScope, started= SharingStarted.WhileSubscribed(5_000), initialValue = null
    )
    fun observeReadings(machineId: String): Flow<List<MachineReading>>{return repository.observeReadings(machineId)}
    fun acknowledgeError(errorId: String){viewModelScope.launch { repository.acknowledgeError(errorId) }}

    fun saveWifiConfiguration(ssid: String, password: String){
        viewModelScope.launch{
            repository.saveWifiConfiguration(WifiConfiguration(ssid=ssid, password=password))
        }
    }

    fun fetchTelemetry(baseUrl: String){
        viewModelScope.launch{
            try{
                repository.fetchAndStoreTelemetry(baseUrl=baseUrl)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }
}
