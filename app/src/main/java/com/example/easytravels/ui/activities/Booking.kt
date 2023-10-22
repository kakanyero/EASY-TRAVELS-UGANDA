package com.example.easytravels.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easytravels.BaseActivity.BaseActivity
import com.example.easytravels.MainActivity
import com.example.easytravels.R
import com.example.easytravels.adapters.BookingsAdapter
import com.example.easytravels.adapters.BusesAdapters
import com.example.easytravels.databinding.ActivityBookingBinding
import com.example.easytravels.databinding.ActivityBusDetailsBinding
import com.example.easytravels.models.BookingClass
import com.example.easytravels.models.Bus
import com.example.easytravels.models.Constants
import com.example.easytravels.mvvm.FireBaseViewModelFactory
import com.example.easytravels.mvvm.FirebaseViewModel
import com.example.easytravels.mvvm.FirebaseViewModelRepository

class Booking : BaseActivity() {
    lateinit var binding: ActivityBookingBinding
    private lateinit var firebaseViewModel : FirebaseViewModel
    private lateinit var listOfBookings:ArrayList<BookingClass>
    private lateinit var bookingsAdapter:BookingsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking)
        setUpActionBar()

        // Initializing the ViewModel
        val repository = FirebaseViewModelRepository()
        val factory = FireBaseViewModelFactory(repository)
        firebaseViewModel = ViewModelProvider(this, factory)[FirebaseViewModel::class.java]

        binding.fabAddBooking.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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

    override fun onResume() {
        super.onResume()
        downloadBookingsFromFireStore()
    }
    private fun downloadBookingsFromFireStore(){
        firebaseViewModel.getBookingsFromCloud(this)
    }

    fun downloadBookingsFromFireStoreSuccess(bookingsList:ArrayList<BookingClass>){
        hideProgressDialog()
        listOfBookings = bookingsList

        if (bookingsList.isNotEmpty()){
            setUpRecyclerView()
            binding.linearLayoutCheckout.visibility = View.VISIBLE

            var transportFee:Double = 0.0
            var luggageFee:Double = 0.0

            for (item in listOfBookings){
                val bookedSeats = item.no_of_seats_booked.toInt()
                if(bookedSeats > 0) {
                    luggageFee = item.luggage_fee.toDouble()
                    val transport = item.travel_fee.toInt()
                    transportFee += (transport * bookedSeats)
                }
            }
            binding.tvTransportFee.text = "${Constants.CURRENCY} $transportFee"

            binding.tvLuggageFee.text = "${Constants.CURRENCY} $luggageFee"

            val grandTotalFee = (transportFee + luggageFee).toString()
            binding.tvTotalCharge.text = "${Constants.CURRENCY} $grandTotalFee"

        }else{
            // show the no bookings available text view and checkout layout
            binding.tvNoBookingsAvailable.visibility = View.VISIBLE
            binding.fabAddBooking.visibility = View.VISIBLE
            binding.linearLayoutCheckout.visibility = View.GONE
        }
    }

    private fun setUpRecyclerView(){
        bookingsAdapter = BookingsAdapter(this)
        binding.tvNoBookingsAvailable.visibility = View.GONE

        /**
         * Make the recycler view visible and add layout manager of choice
         */
        binding.recyclerViewBookings.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(this@Booking)
            adapter = bookingsAdapter
        }

         Log.e("Bookings:", "$listOfBookings")
        bookingsAdapter.diff.submitList(listOfBookings)
    }

    fun deleteBooking(bookingId:String){
        firebaseViewModel.deleteBookingFromCloud(this, bookingId)
    }

    fun deleteBookingSuccess() {
        hideProgressDialog()
        downloadBookingsFromFireStore()
        Toast.makeText(this, "Booking deleted successfully", Toast.LENGTH_LONG).show()
    }

    fun updateBooking(context: Booking, booking_id:String, itemHashMap:HashMap<String, Any>){
        firebaseViewModel.updateBooking(context, booking_id, itemHashMap)
    }
    fun updateBookingSuccess(){
//        hideProgressDialog()
        Toast.makeText(this, "Booking updated successfully", Toast.LENGTH_LONG).show()
        downloadBookingsFromFireStore()
    }


}