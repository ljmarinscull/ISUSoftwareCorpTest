package com.project.acmetest.utils

import androidx.room.TypeConverter
import java.time.LocalDate

/**
 * A class to store and retrieve the LocalDate datatype.
 * @param <T>
 */
class LocalDateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}