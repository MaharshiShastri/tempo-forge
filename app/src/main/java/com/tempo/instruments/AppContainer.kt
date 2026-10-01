package com.tempo.instruments

import com.tempo.instruments.database.TempoDatabase
import com.tempo.instruments.repository.RoomTempoDataSource
import com.tempo.instruments.repository.TempoDataSource
import com.tempo.instruments.repository.TempoRepository

class AppContainer(
    private val database: TempoDatabase
) {

    private val dataSource: TempoDataSource by lazy {
        RoomTempoDataSource(
            machineDao = database.machineDao(),
            readingDao = database.machineReadingDao(),
            errorDao = database.errorEventDao()
        )
    }

    val repository: TempoRepository by lazy {
        TempoRepository(dataSource)
    }
}