package com.tempo.instruments.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NetworkConfigurationDao {
    @Query("SELECT * FROM network_configuration WHERE id=1")
    fun observeConfiguration(): Flow<NetworkConfigurationEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveConfiguration(configuration: NetworkConfigurationEntity)

    @Query("DELETE FROM network_configuration WHERE id=1")
    suspend fun clearConfiguration()
}