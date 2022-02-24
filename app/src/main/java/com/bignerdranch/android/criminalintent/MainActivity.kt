package com.bignerdranch.android.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Stores the fragment of our fragment container.
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)
        // Checks to see if we had a fragment in our fragment container. If not we
        // create an instance of the CrimeFragment()
        if(currentFragment == null) {
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}