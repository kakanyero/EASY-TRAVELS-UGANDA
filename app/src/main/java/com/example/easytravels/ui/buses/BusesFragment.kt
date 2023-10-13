package com.example.easytravels.ui.buses

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easytravels.adapters.BusesAdapters
import com.example.easytravels.databinding.FragmentBusesBinding
import com.example.easytravels.models.Bus
import com.example.easytravels.mvvm.FireBaseViewModelFactory
import com.example.easytravels.mvvm.FirebaseViewModel
import com.example.easytravels.mvvm.FirebaseViewModelRepository
import com.example.easytravels.ui.activities.AddBus

class BusesFragment : Fragment() {

    private var _binding : FragmentBusesBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseViewModel : FirebaseViewModel
    private lateinit var busesAdapter: BusesAdapters
    private lateinit var listOfBuses:ArrayList<Bus>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBusesBinding.inflate(inflater,  container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializing the ViewModel
        val repository = FirebaseViewModelRepository()
        val factory = FireBaseViewModelFactory(repository)
        firebaseViewModel = ViewModelProvider(this, factory)[FirebaseViewModel::class.java]

        // Add a bus
        binding.fabAddBus.setOnClickListener {
            val intent = Intent(requireActivity(), AddBus::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        downloadBusListFromFireStore()
    }

    private fun downloadBusListFromFireStore(){
        firebaseViewModel.downloadBusDetailsFromCloud(this)
    }

    fun downloadBusListFromFireStoreSuccess(busList:ArrayList<Bus>){
        // Check If the list is not empty then we initialize the variable listOfBuses
        // with this passed/received list
        if (busList.size > 0){
            // initialize the variable "listOfBuses"
            listOfBuses = busList

            // Since the bus list is not empty, then the recycler view can be started
            setUpRecyclerView()
        }
    }

    private fun setUpRecyclerView(){
        busesAdapter = BusesAdapters(requireActivity())
        binding.tvNoBusAvailable.visibility = View.GONE

        /**
         * Make the recycler view visible and add layout manager of choice
         */
        binding.recyclerViewBuses.apply {
            visibility = View.VISIBLE
            layoutManager = GridLayoutManager(requireActivity(), 1)
            adapter = busesAdapter
        }

        Log.e("BUSES:", "$listOfBuses")
        busesAdapter.diff.submitList(listOfBuses)
    }


}