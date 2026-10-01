package com.tempo.instruments.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MachineEntity::class, MachineReadingEntity::class, ErrorEventEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class TempoDatabase : RoomDatabase(){
    abstract fun machineDao(): MachineDao
    abstract fun machineReadingDao(): MachineReadingDao
    abstract fun errorEventDao(): ErrorEventDao
}