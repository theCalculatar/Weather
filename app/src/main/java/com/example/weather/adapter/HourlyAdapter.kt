package com.example.weather.adapter

import android.text.format.Time
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.model.Hourly
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class HourlyAdapter(private val hourlyArray: ArrayList<Hourly>):RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_hourly_forecast,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hourlyArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hour = hourlyArray[position]
        val weather = hour.weather[0]
        // UTC time var
        val time: Long = hour.dt

        //converted
        val date = SimpleDateFormat("HH:mm").format(time*1000)

        holder.time.text = if (position==0){
            "Now"
        }else date


        "${hour.temp.toInt()}℃".also {
            holder.main.text = it
        }
        Glide.with(holder.itemView.context)
            .load("https://openweathermap.org/img/w/${weather.icon}.png")
            .into(holder.icon)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val time: TextView = itemView.findViewById(R.id.time)
        val icon: ImageView = itemView.findViewById(R.id.weather_icon)
        val main: TextView = itemView.findViewById(R.id.main)

    }
}
