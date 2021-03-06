package com.bignerdranch.android.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.criminalintent.Crime

// Tells Room that this class represents a database.
// First parameter is a list of entity classes (Crime), these are the tables
// Second parameter is the version of the database. (This is set by the developer),
// convention is to start with 1 and update it as the database is modified.
@Database(entities = [ Crime::class], version=2)
@TypeConverters(CrimeTypeConverters::class) //Allows the database to use the conversion functions
abstract class CrimeDatabase : RoomDatabase() {

    // Sets up an abstract function so when the database is generated Room will also generate
    // a concrete implementation of the DAO
    abstract fun CrimeDao(): CrimeDao
}

// Updates the database structure by using an alter statment to add a column and migrating data
// to the new database.
val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Crime ADD COLUMN suspect TEXT NOT NULL DEFAULT ''"
        )
    }
}