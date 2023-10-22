package com.example.easytravels.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.easytravels.R
import com.example.easytravels.databinding.CustomBookingLayoutBinding
import com.example.easytravels.models.BookingClass
import com.example.easytravels.models.Constants
import com.example.easytravels.ui.activities.Booking

class BookingsAdapter(private val context: Booking):RecyclerView.Adapter<BookingsAdapter.MyViewHolder>() {

    class MyViewHolder(val customBooking:CustomBookingLayoutBinding):RecyclerView.ViewHolder(customBooking.root)

    private val diffCallback = object : DiffUtil.ItemCallback<BookingClass>(){
        override fun areItemsTheSame(oldItem: BookingClass, newItem: BookingClass): Boolean {
            return oldItem.booking_id == newItem.booking_id && oldItem.bus_no == newItem.bus_no &&
                    oldItem.bus_driver == newItem.bus_driver && oldItem.bus_route == newItem.bus_route &&
                    oldItem.bus_id == newItem.bus_id && oldItem.no_of_seats_booked == newItem.no_of_seats_booked
        }

        override fun areContentsTheSame(oldItem: BookingClass, newItem: BookingClass): Boolean {
            return oldItem == newItem
        }

    }

    val diff = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CustomBookingLayoutBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentBooking = diff.currentList[position]

        holder.customBooking.tvBookingRoute.text = currentBooking.bus_route
        holder.customBooking.tvBookingPrice.text = currentBooking.travel_fee
        holder.customBooking.tvSeatsBooked.text = currentBooking.no_of_seats_booked
        holder.customBooking.tvSeatsAvailable.text = currentBooking.seats_available

        holder.customBooking.buttonCancelBooking.setOnClickListener {
            context.deleteBooking(currentBooking.booking_id)
        }

        if (currentBooking.seats_available.toInt() == 0){
            // If There are no seats left then hide the add and minus icons
            holder.customBooking.seatRemoveBtn.visibility = View.GONE
            holder.customBooking.seatsAddBtn.visibility = View.GONE

            // Set the number of seats booked as no seats available
            holder.customBooking.tvSeatsBooked.text = "No seats available for booking"
            holder.customBooking.tvSeatsBooked.setTextColor(
                ContextCompat.getColor(
                    context, R.color.appColor
                )
            )
        }
        else{
            // Make the add and minus buttons be visible
            holder.customBooking.seatRemoveBtn.visibility = View.VISIBLE
            holder.customBooking.seatsAddBtn.visibility = View.VISIBLE
        }


        // Reducing the number of seats booked
        holder.customBooking.seatRemoveBtn.setOnClickListener {
            /**
             * check if the number of seats booked is 1, which means if the - button is clicked, then
             * the booking has to be deleted
             */
            if (currentBooking.no_of_seats_booked.toInt() == 1){
                context.deleteBooking(currentBooking.booking_id)
            }
            else{
                // Then else we still have seats, reducing on number of bookings is possible
                val seatsBooked = currentBooking.no_of_seats_booked.toInt()
                val itemHashMap = HashMap<String, Any>()
                // Setting the hashmap or the exact field/attribute to update
                itemHashMap[Constants.NO_OF_SEATS_BOOKED] = (seatsBooked - 1).toString()

                context.updateBooking(context, currentBooking.booking_id, itemHashMap)
            }
        }

        // Adding to the seats booked
        holder.customBooking.seatsAddBtn.setOnClickListener {
            /**
             * Check first if the number of seats booked is less than the seats available
             * if they are less, then one can still book more seats and else a message is displayed
             * showing that the seats for booking are done
             */
            val noOfSeatsBooked = currentBooking.no_of_seats_booked
            if (noOfSeatsBooked.toInt() < currentBooking.seats_available.toInt()){
                val itemHashMap = HashMap<String, Any>()
                // Setting the hashmap or the exact field/attribute to update
                itemHashMap[Constants.NO_OF_SEATS_BOOKED] = (noOfSeatsBooked.toInt() + 1).toString()

                context.updateBooking(context, currentBooking.booking_id, itemHashMap)
            }else{
                context.showErrorSnackBar("No seats available for booking", true)
            }
        }
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }
}