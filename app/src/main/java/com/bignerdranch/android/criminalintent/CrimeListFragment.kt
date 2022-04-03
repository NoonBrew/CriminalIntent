package com.bignerdranch.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }
    // callbacks allow our fragment to call functions on the hosting activity
    private var callbacks: Callbacks? = null

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }
    // Called when a fragment is attached to an activity. Context is the activity hosting.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView =
            view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe( // registers an observer on the liveData instance
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            })
    }
    // On detach from the activity we set callback to null since we can't be sure
    // the activity still exists
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    // This is called when the fragment is inflated to grab the current listViewModel information
    private fun updateUI(crimes: List<Crime>) {
        // crimes are stored and passed to the adapter as a mutable list
        adapter = CrimeAdapter(crimes)
        // the adapter is then passed to the crimeRecyclerView.
        crimeRecyclerView.adapter = adapter
    }

    private inner class CrimeHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        // initiates the object crime from our Crime class.
        private lateinit var crime: Crime
        // we also late initiate our contactPoliceButton because not ever crimeView will need the
        // button.
        private lateinit var contactPoliceButton: Button

        // Sets up our other views that we know will be true for each crime.
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)


        init {
            // Tells the CrimeHolder to set an onClickListener for this itemView.
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            // Bind function is passed a crime from the adapter
            this.crime = crime
            // If the crime has a true value for requiresPolice we wire up the contactPoliceButton
            // and add it to the view.
//            if (crime.requiresPolice){
//                contactPoliceButton = itemView.findViewById(R.id.crime_requires_police)
//                contactPoliceButton.setOnClickListener { // Set a listener to make a toast if its pressed
//                    Toast.makeText(context, "Call 911", Toast.LENGTH_SHORT).show()
//                }
//            } // TODO Re-add when not using pre-populated data.
            // Passes the data to our textViews
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.formattedDate()

            // If the crime is solved we will make our image visible other wise it won't be created.
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }


        override fun onClick(v: View) { // Passes a toast message if a view is clicked.
//            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
            // stores a UUID of our crime selected in our callbacks holder.
            callbacks?.onCrimeSelected(crime.id)
        }

    }

    // Inner class adapter. Responsible for holding the individual views of each crime
    // Is passed our List of crimes and our CrimeHolder inner class.
    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {

        // Creates a view for the crime, creates a different view depending the viewType
        // the viewType is determined by our getItemViewType.
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = when (viewType) {
                0 -> layoutInflater.inflate(R.layout.list_item_crime, parent, false)
                else -> layoutInflater.inflate(R.layout.list_item_serious_crime, parent, false)
            }
            // Passes the view to the CrimeHolder so it knows which view it is using.
            return CrimeHolder(view)
        }
        // Gets the size of the list being passed to adapter.
        override fun getItemCount() = crimes.size

        // Passes a crime object at a specific position to our ViewHolders bind function.
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemViewType(position: Int): Int {
            // Looks at the crime object at a given position and returns a Int value
            val crime = crimes[position]
            // If the requiresPolice attribute is true it returns 1 otherwise 0 this is passed on as
            // the view type.
            return when {
               // crime.requiresPolice -> 1 // TODO re-add when not using pre-populated database.
                else -> 0
            }
        }
    }

    // Returns a new Instance of the fragment when called.
    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}