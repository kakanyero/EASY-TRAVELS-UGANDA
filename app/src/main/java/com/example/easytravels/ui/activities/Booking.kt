package com.example.easytravels.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivityBookingBinding
import com.example.easytravels.models.BookingClass
import com.example.easytravels.models.Constants

class Booking : BaseActivity(), View.OnClickListener {
    private lateinit var binding:ActivityBookingBinding

    private var mBusNumberPlate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking)

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

    override fun onClick(v: View?) {
        if(v != null) {
            when (v.id) {
                R.id.button_make_booking -> {
                    //Add product to cart
                    makeSeatBooking()
                }
            }
        }
    }

    private fun makeSeatBooking() {
        val booking = BookingClass()
    }


}