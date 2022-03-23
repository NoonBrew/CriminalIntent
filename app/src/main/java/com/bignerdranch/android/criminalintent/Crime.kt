package com.bignerdranch.android.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

// Sets the parameters for our data class and assigns default values.
@Entity // Indicates the che class defines the structure of a table.
data class Crime(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false,
                 //var requiresPolice: Boolean = false // TODO re-add later
) { // @PrimaryKey sets the PrimaryKey column

    fun formattedDate(): String { // Function creates a formatted String value for our date.
        return SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault()).format(date)
    }
}