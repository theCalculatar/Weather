package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class DailyAdapter(private val hourlyArray: ArrayList<String>):RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_extras,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hourlyArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }
}
