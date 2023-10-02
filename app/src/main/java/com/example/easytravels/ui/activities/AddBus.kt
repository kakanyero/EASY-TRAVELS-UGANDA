package com.example.easytravels.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easytravels.MainActivity
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivityAddBusBinding
import com.example.easytravels.models.Bus
import com.example.easytravels.mvvm.FireBaseViewModelFactory
import com.example.easytravels.mvvm.FirebaseViewModel
import com.example.easytravels.mvvm.FirebaseViewModelRepository
import com.example.easytravels.ui.busses.BussesFragment

class AddBus : AppCompatActivity() {

    lateinit var binding: ActivityAddBusBinding
    private lateinit var firebaseViewModel:FirebaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_bus)

        // Initializing the ViewModel
        val repository = FirebaseViewModelRepository()
        val factory = FireBaseViewModelFactory(repository)
        firebaseViewModel = ViewModelProvider(this, factory)[FirebaseViewModel::class.java]

        binding.btnAddBus.setOnClickListener {
            addBusToCloud()
        }
    }

    private fun addBusToCloud(){
        val busNumber = binding.edBusNo.text.toString()
        val busRoute = binding.edBusRoute.text.toString()
        val busDriver = binding.edBusDriver.text.toString()
        val passengers = binding.edNumberOfPassengers.text.toString().toInt()

        val busToAddToCloud = Bus(busNumber, busRoute, busDriver, passengers)

        // Saving
        firebaseViewModel.storeBusDetailsToCloud(this, busToAddToCloud)

    }
    fun addBusDetailsToCloudSuccess() {
        Toast.makeText(this, "Bus details added successfully", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}