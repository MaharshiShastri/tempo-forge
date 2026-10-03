package com.tempo.instruments

import android.app.Application
import androidx.room.Room
import com.tempo.instruments.database.TempoDatabase
import com.tempo.instruments.database.TempoDatabaseSeeder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TempoApplication : Application() {

    val database: TempoDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            TempoDatabase::class.java,
            "tempo_forge.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    val appContainer: AppContainer by lazy {
        AppContainer(database)
    }

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            database.machineDao().insertMachines(
                TempoDatabaseSeeder.machines()
            )

            database.machineReadingDao().insertReadings(
                TempoDatabaseSeeder.readings()
            )

            database.errorEventDao().insertErrors(
                TempoDatabaseSeeder.errors()
            )
        }
    }
}