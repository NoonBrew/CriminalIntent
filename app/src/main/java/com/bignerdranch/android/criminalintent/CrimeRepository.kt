package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bignerdranch.android.criminalintent.database.CrimeDatabase
import java.util.*

private const val DATABASE_NAME = "crime-database"

// CrimeRepo is a singleton, only ever one instance in the app process
// Singleton exists as long as the app stays in memory and can keep properties available through
// lifecycle changes
class CrimeRepository private constructor(context: Context){

    // Creates a concrete database from the abstract CrimeDatabase
    private val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext, // Context
        CrimeDatabase::class.java, // DatabaseClass
        DATABASE_NAME // Database Name
    ).build()

    private val crimeDao = database.CrimeDao() // calls the Database access Object class.

    // Calls the functions from the CrimeDao
    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    companion object{
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context){ // Initializes a new instance of the repository if it is null
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository { // Gets the repository and throws an exception if there is none.
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}