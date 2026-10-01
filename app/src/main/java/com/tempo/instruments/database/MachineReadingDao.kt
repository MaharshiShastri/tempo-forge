package com.tempo.instruments.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tempo.instruments.data.MachineReading
import kotlinx.coroutines.flow.Flow

@Dao
interface MachineReadingDao {
    @Query("""
        SELECT * FROM machine_readings 
        WHERE machineId = :machineId 
        ORDER BY timestamp ASC
        """
    )
    fun observeReadings(machineId: String): Flow<List<MachineReadingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReadings(readings: List<MachineReadingEntity>)
}