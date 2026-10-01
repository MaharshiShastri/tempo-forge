package com.tempo.instruments.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ErrorEventDao{
    @Query(
        """
            SELECT * FROM error_events
            ORDER BY timestamp ASC
        """
    )
    fun observeErrors(): Flow<List<ErrorEventEntity>>

    @Query("""
        SELECT * FROM error_events
        WHERE id = :errorId
    """)
    fun observeError(errorId: String): Flow<ErrorEventEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertErrors(errors: List<ErrorEventEntity>)

    @Query("""
        UPDATE error_events
        SET acknowledged = 1
        WHERE id = :errorId
    """)
    suspend fun acknowledgeError(errorId: String)
}
