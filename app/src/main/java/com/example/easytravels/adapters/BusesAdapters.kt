package com.example.easytravels.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.easytravels.R
import com.example.easytravels.databinding.CustomBusLayoutBinding
import com.example.easytravels.models.Bus
import com.example.easytravels.models.Constants
import com.example.easytravels.ui.activities.Booking

class BusesAdapters(private val context: Context):RecyclerView.Adapter<BusesAdapters.CustomViewHolder>() {

    class CustomViewHolder(val customBus:CustomBusLayoutBinding):RecyclerView.ViewHolder(customBus.root)

    // Creating a diff callback
    private val differCallback = object : DiffUtil.ItemCallback<Bus>(){
        override fun areItemsTheSame(oldItem: Bus, newItem: Bus): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.bus_route == newItem.bus_route &&
                    oldItem.bus_no == newItem.bus_no &&
                    oldItem.bus_driver == newItem.bus_driver
        }

        override fun areContentsTheSame(oldItem: Bus, newItem: Bus): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusesAdapters.CustomViewHolder {
        return CustomViewHolder(
            CustomBusLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BusesAdapters.CustomViewHolder, position: Int) {
        val currentBus = diff.currentList[position]

        holder.customBus.tvBusNo.text = currentBus.bus_no
        holder.customBus.tvBusDriver.text = currentBus.bus_driver
        holder.customBus.tvBusRoute.text = currentBus.bus_route
        holder.customBus.tvTotalSeat.text = currentBus.num_of_seats.toString()

        // Check if there is still seats
        if (currentBus.num_of_seats > 0){
            holder.customBus.tvBusStatus.setBackgroundColor(
                ContextCompat.getColor(context, R.color.active_state_color)
            )
        }else{
            // if the number of seats is less than or equal to 0 then, bus not clickable
            holder.customBus.tvBusStatus.setBackgroundColor(
                ContextCompat.getColor(context, R.color.appColor)
            )
        }

        // Able to view the bus and make a booking
        holder.itemView.setOnClickListener {
            val intent = Intent(context, Booking::class.java)
            context.startActivity(intent)
            intent.putExtra(Constants.BUS_NUMBER_PLATE, currentBus.bus_no)
            intent.putExtra("bus_driver", currentBus.bus_driver)
            intent.putExtra("bus_route", currentBus.bus_route)
            intent.putExtra("number_of_seats", currentBus.num_of_seats)
        }
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

}