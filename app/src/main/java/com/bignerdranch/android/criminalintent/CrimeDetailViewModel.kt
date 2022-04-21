package com.bignerdranch.android.criminalintent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

class CrimeDetailViewModel() : ViewModel() {
    // gets the instance of the database
    private val crimeRepository = CrimeRepository.get()
    // stores the ID of a crime being displayed.
    private val crimeIdLiveData = MutableLiveData<UUID>()

    // Takes the liveData crime noted by the CrimeID from load crime and passes it to our Repo.
    var crimeLiveData: LiveData<Crime?> =
        Transformations.switchMap(crimeIdLiveData) { crimeId ->
            crimeRepository.getCrime(crimeId)
        }

    fun loadCrime(crimeId: UUID) { // gets passed a crimeID that is set as the value for a liveData
        crimeIdLiveData.value = crimeId
    }

    fun saveCrime(crime: Crime) {
        crimeRepository.updateCrime(crime)
    }

    fun getPhotoFile(crime: Crime): File {
        return crimeRepository.getPhotoFile(crime)
    }
}