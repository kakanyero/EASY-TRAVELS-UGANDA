package com.example.easytravels.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivityAddBusBinding
import com.example.easytravels.databinding.ActivityBookingBinding
import com.example.easytravels.models.Constants
import com.example.easytravels.mvvm.FireBaseViewModelFactory
import com.example.easytravels.mvvm.FirebaseViewModel
import com.example.easytravels.mvvm.FirebaseViewModelRepository

class Booking : AppCompatActivity() {

    lateinit var binding: ActivityBookingBinding
    private lateinit var firebaseViewModel:FirebaseViewModel
    private var mBusNumberPlate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking)

        // Setup action bar
        setUpActionBar()

        /**
         * First checking if the intent has the extra data including the bus number
         * for the specific bus to display
         */
        if(intent.hasExtra(Constants.BUS_NUMBER_PLATE)){
            // Storing the bus number plate in the mBusNumberPlate global variable
            mBusNumberPlate = intent.getStringExtra(Constants.BUS_NUMBER_PLATE)!!

            // Get the bus details data from intent and assign it to the views in this activity
            val busDriver = intent.getStringExtra("bus_driver")
            val busRoute = intent.getStringExtra("bus_route")
            val numberOfSeats = intent.getStringExtra("number_of_seats")

            binding.tvDetailsBusDriver.text = busDriver
            binding.tvDetailsBusRoute .text = busRoute
            binding.tvDetailsBusNo.text = mBusNumberPlate
            binding.tvDetailsTotalSeat.text = numberOfSeats
        }
    }

    private fun setUpActionBar(){
        val myToolBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.booking_toolbar)
        setSupportActionBar(myToolBar)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }

        myToolBar.setNavigationOnClickListener { onBackPressed() }
    }
}