package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class CrimeListViewModel : ViewModel() {

    val crimes = mutableListOf<Crime>()
    // creates a bunch of example data for our RecyclerViewModel
    init {
        for (i in 0 until 100) {
            val crime = Crime()
            crime.title = "Crime #$i"
            // Makes ever other crime a solved crime.
            crime.isSolved = i % 2 == 0
            // Randomly assigns requiresPolice to be true or false.
            crime.requiresPolice = Random.nextBoolean()
            crimes += crime
        }
    }
}