package com.tempo.instruments.database

import androidx.room.TypeConverter
import com.tempo.instruments.data.ErrorSeverity

class Converters {
    @TypeConverter
    fun fromErrorSeverity(value: ErrorSeverity): String{
        return value.name
    }

    @TypeConverter
    fun toErrorSeverity(value: String): ErrorSeverity{
        return ErrorSeverity.valueOf(value)
    }
}