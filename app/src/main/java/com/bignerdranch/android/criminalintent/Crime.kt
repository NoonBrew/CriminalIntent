package com.bignerdranch.android.criminalintent

import java.util.*
// Sets the parameters for our data class and assigns default values.
data class Crime(val id: UUID = UUID.randomUUID(),
                var title: String = "",
                var date: Date = Date(),
                var isSolved: Boolean = false,
                var requiresPolice: Boolean = false) {
}