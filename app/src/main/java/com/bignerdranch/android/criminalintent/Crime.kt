package com.bignerdranch.android.criminalintent

import java.text.SimpleDateFormat
import java.util.*
// Sets the parameters for our data class and assigns default values.
data class Crime(val id: UUID = UUID.randomUUID(),
                var title: String = "",
                var date: Date = Date(),
                var isSolved: Boolean = false,
                var requiresPolice: Boolean = false) {

    fun formattedDate(): String { // Function creates a formatted String value for our date.
        return SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault()).format(date)
    }
}