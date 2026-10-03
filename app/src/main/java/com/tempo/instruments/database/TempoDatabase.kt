package com.tempo.instruments.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        MachineEntity::class,
        MachineReadingEntity::class,
        ErrorEventEntity::class,
        NetworkConfigurationEntity::class
    ],
    version = 2,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class TempoDatabase : RoomDatabase(){
    abstract fun machineDao(): MachineDao
    abstract fun machineReadingDao(): MachineReadingDao
    abstract fun errorEventDao(): ErrorEventDao

    abstract fun networkConfigurationDao(): NetworkConfigurationDao
}