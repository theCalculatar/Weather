package com.example.weather.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.model.Daily
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class DailyAdapter(private val dailyArray: ArrayList<Daily>):RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_daily_forecast,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dailyArray.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dayPosition = dailyArray[position]
        val weather = dayPosition.temp
        val time = dayPosition.dt
        val date = SimpleDateFormat("MM/dd/yyyy HH:ss").format(time*1000)
        val year = date.split("/")[2].split(" ")[0].toInt()
        val day = date.split("/")[1].toInt()
        val month = date.split("/")[0].toInt()

        val locale = LocalDate.of(year,month,day)


        locale.dayOfWeek.also {
            holder.day.text = it.name.lowercase()
        }
        "${weather.max.toInt()}℃".also {
            holder.higherTempt.text = it
        }
        "${weather.min.toInt()}℃".also {
            holder.lowerTemp.text = it
        }
        Glide.with(holder.itemView.context)
            .load("https://openweathermap.org/img/w/${dayPosition.weather[0].icon}.png")
            .into(holder.icon)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val day:TextView = itemView.findViewById(R.id.day)
        val higherTempt:TextView = itemView.findViewById(R.id.higher_temp)
        val lowerTemp:TextView = itemView.findViewById(R.id.lower_temp)
        val icon:ImageView = itemView.findViewById(R.id.weather_icon)

    }
}
