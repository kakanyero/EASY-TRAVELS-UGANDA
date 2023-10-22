package com.example.easytravels.models

import com.google.firebase.auth.FirebaseAuth

object Constants {
    const val BOOKING_COLLECTION: String = "Bookings"
    const val BUS_COLLECTION:String = "Buses"
    const val BUS_ID:String = "bus_id"
    const val USER_ID:String = "user_id"
    const val DEFAULT_NUMBER_OF_SEATS_BOOKED:String = "1"
    const val USERS_COLLECTION:String = "Users"
    const val NO_OF_SEATS_BOOKED:String = "no_of_seats_booked"
    const val CURRENCY:String = "UGX "
    const val BUS_ROUTES_COLLECTION:String = "Bus_routes"
    // This "BUS_ROUTE" value is a variable in model class "BookingClass"
    const val BUS_ROUTE:String = "bus_route"

    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }
}