package com.example.easytravels.ui.activities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easytravels.BaseActivity.BaseActivity
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivityBusDetailsBinding
import com.example.easytravels.models.BookingClass
import com.example.easytravels.models.Bus
import com.example.easytravels.models.Constants
import com.example.easytravels.models.Route
import com.example.easytravels.mvvm.FireBaseViewModelFactory
import com.example.easytravels.mvvm.FirebaseViewModel
import com.example.easytravels.mvvm.FirebaseViewModelRepository
import com.google.firebase.firestore.FirebaseFirestore

class BusDetails : BaseActivity() {

    lateinit var binding: ActivityBusDetailsBinding
    private lateinit var firebaseViewModel:FirebaseViewModel
    private lateinit var fBusDetails:Bus
    private var busId:String = ""
    private var luggageFee:Int = 0
    lateinit var mRoute: Route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bus_details)

        // Setup action bar
        setUpActionBar()


        val repository = FirebaseViewModelRepository()
        val factory = FireBaseViewModelFactory(repository)
        firebaseViewModel = ViewModelProvider(this, factory)[FirebaseViewModel::class.java]

        if (intent.hasExtra(Constants.BUS_ID)){
            busId = intent.getStringExtra(Constants.BUS_ID)!!
        }

        // Get bus details
        getExtraBusDetailsFromCloud()

        // Make a booking
        binding.buttonMakeBooking.setOnClickListener {
            if (binding.haveLuggage.isChecked){
                luggageFee = 10000
            }
            makeBooking()
        }

        binding.buttonGoToBooking.setOnClickListener {
            startActivity(Intent(this, Booking::class.java))
        }
    }

    private fun setUpActionBar(){
        val myToolBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.bus_details_toolbar)
        setSupportActionBar(myToolBar)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }

        myToolBar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getExtraBusDetailsFromCloud(){
        firebaseViewModel.getExtraBusDetailsFromCloud(this, busId)
    }

    fun getExtraBusDetailsFromCloudSuccess(busDetails: Bus) {
        // Hide the progress dialog
        hideProgressDialog()

        // Initialize the fBusDetails variable
        fBusDetails = busDetails


        // Get bus route
//        getBusRoute(fBusDetails.bus_route)

        // Assign bus details to the views
        binding.tvDetailsBusDriver.text = busDetails.bus_driver
        binding.tvDetailsBusRoute .text = busDetails.bus_route
        binding.tvDetailsBusNo.text = busDetails.bus_no
//        binding.tvDetailsTravelFee.text = mRoute.route_fee
        binding.tvDetailsTotalSeat.text = busDetails.num_of_seats.toString()

        /**
         * Checking if there is a booking already made, if there is then hide the booking button and show
         * thee GO_TO_BOOKING button
         */
        FirebaseFirestore.getInstance().collection(Constants.BOOKING_COLLECTION)
            // The whereEqualTo Clause will filter to load bookings for only user logged in
            .whereEqualTo(Constants.USER_ID, Constants.getCurrentUserID())
            .get()
            .addOnSuccessListener {document ->
                if (document.documents.size > 0){
                    binding.buttonMakeBooking.visibility = View.GONE
                    binding.buttonGoToBooking.visibility = View.VISIBLE

                    showErrorSnackBar("You have a booking already", true)
                }else{
                    // make a booking since there is none made already
                    binding.buttonMakeBooking.visibility = View.VISIBLE
                    binding.buttonGoToBooking.visibility = View.GONE
                }
            }
            .addOnFailureListener {
                Log.e("CheckingExists: ", "No booking available")
            }

        /**
         * Check if seats are still available for booking and if they are not, then prevent the client from
         * proceeding to make a booking by making the button visibility gone
         */
        if (busDetails.num_of_seats == 0){
            binding.buttonMakeBooking.visibility = View.GONE
            binding.buttonGoToBooking.visibility = View.GONE
            binding.tvNoSeatsAvailable.visibility = View.VISIBLE
        }else{
            binding.tvNoSeatsAvailable.visibility = View.GONE
            binding.buttonMakeBooking.visibility = View.VISIBLE
        }

    }

    // Get the transport Fee of the route the bus selected takes
//    private fun getBusRoute(bus_route:String) {
//        firebaseViewModel.getBusRoute(this, bus_route)
//    }
//    fun getBusRouteSuccess(route: Route){
//        mRoute = route
//
//        Log.e("Bus route: ", "${mRoute.route_fee}")
//    }


    // Make a booking
    private fun makeBooking(){
        val booking = BookingClass(
            Constants.getCurrentUserID(),
            Constants.DEFAULT_NUMBER_OF_SEATS_BOOKED,
            fBusDetails.bus_no, fBusDetails.bus_route, fBusDetails.bus_driver,
            "35000", luggageFee.toString(), fBusDetails.num_of_seats.toString(), fBusDetails.id
        )
        firebaseViewModel.makeBooking(this, booking)

        // TODO: "Need to get tranz fees according to routes"
    }

    fun makeBookingSuccess(){
        val intent = Intent(this, Booking::class.java)
        Toast.makeText(this, "Booking made successfully", Toast.LENGTH_LONG).show()
        // Hide the progress dialog
        hideProgressDialog()
        startActivity(intent)
    }
}