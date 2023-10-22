package com.example.easytravels.models

data class BookingClass(
    val user_id:String = "",
    val no_of_seats_booked:String = "",
    val bus_no:String = "",
    val bus_route:String = "",
    val bus_driver:String = "",
    val travel_fee:String = "",
    val luggage_fee:String = "",
    val seats_available:String = "",
    val bus_id:String = "",
    var booking_id:String = ""
)
