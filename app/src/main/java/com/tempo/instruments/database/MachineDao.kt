package com.tempo.instruments.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MachineDao{
    @Query("SELECT * FROM machines")
    fun observeMachines(): Flow<List<MachineEntity>>

    @Query("SELECT * FROM machines WHERE id = :machineId")
    fun observerMachine(machineId: String): Flow<MachineEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMachines(machines: List<MachineEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMachine(machine: MachineEntity)

    @Query("UPDATE machines SET temperature = :temperature, pressure = :pressure, isOnline = :isOnline WHERE id = :machineId")
    suspend fun updateTelemetry(machineId: String, temperature: Double, pressure: Double, isOnline: Boolean): Int
}