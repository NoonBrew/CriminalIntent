package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment

class CrimeFragment: Fragment() {

    private lateinit var crime: Crime
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // creates an instance of our data class.
        crime = Crime()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        // Wires all our buttons. The book used the AS features to tell the fragment what it is
        // Not sure if this is actually needed or if it is for readability.
        titleField = view.findViewById(R.id.crime_title) as EditText
        dateButton = view.findViewById(R.id.crime_date) as Button
        solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox
        // Apply a date to the button and disables it for now. will be enabled in future chapters.
        dateButton.apply {
            text = crime.date.toString()
            isEnabled = false
        }

        return view
    }
    // Creates a listener that checks the text in the TitleField and on pause will store the
    // data in a string in our Crime object and recall it when the app is restored.
    // Useful to protect the editText on rotation so we do not have to out bundle.
    override fun onStart() {
        super.onStart()

        val titleWatcher = object: TextWatcher {

            override fun beforeTextChanged(sequence: CharSequence?,
                                           start: Int,
                                           count: Int,
                                           after: Int
            ){
                // this space intentionally left blank
            }

            override fun onTextChanged(sequence: CharSequence?,
                                       start: Int,
                                       before: Int,
                                       count: Int
            ) {
                crime.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // this one too.
            }
        }

        titleField.addTextChangedListener(titleWatcher)
        // Also remembers if the check box was selected or not.
        solvedCheckBox.apply {
            setOnCheckedChangeListener{_, isChecked ->
                crime.isSolved = isChecked
            }
        }
    }
}