package com.bignerdranch.android.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bignerdranch.android.criminalintent.Crime
import java.util.*

@Dao // Data access Object
// Used to contain functions that interact with the Database (Query).
interface CrimeDao {

    @Query("SELECT * from crime")
    fun getCrimes(): LiveData<List<Crime>>
    // By returning a LiveData it signals Room to run on
    // Background thread

    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): LiveData<Crime?>

    @Update
    fun updateCrime(crime: Crime)

    @Insert
    fun addCrime(crime: Crime)
}