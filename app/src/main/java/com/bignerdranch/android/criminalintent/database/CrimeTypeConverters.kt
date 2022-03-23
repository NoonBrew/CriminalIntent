package com.bignerdranch.android.criminalintent.database

import androidx.room.TypeConverter
import java.util.*

// Class tells how to convert Date and UUID to a primitive type to be stored in a
// SQLite database. This class must be explicitly added to the Database class so the functions
// can be used.
class CrimeTypeConverters {

    @TypeConverter // @TypeConverter tells room this is used to convert a data
    fun fromDate(date: Date?): Long? { // Converts date into a long int for the database to store
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? { //Converts a long back into a date.
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? { // Converts a string back into a UUID
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? { // Converts a UUID into a string for storage.
        return uuid?.toString()
    }
}