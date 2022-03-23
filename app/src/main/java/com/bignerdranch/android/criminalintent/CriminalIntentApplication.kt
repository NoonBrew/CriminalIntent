package com.bignerdranch.android.criminalintent

import android.app.Application

class CriminalIntentApplication : Application() { // Application classes are used for set-up

    // Will be called by the system when the application is first loaded
    // Similar to Activity.onCreate(..)
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}