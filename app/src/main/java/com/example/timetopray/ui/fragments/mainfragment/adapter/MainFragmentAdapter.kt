package com.example.timetopray.ui.fragments.mainfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.timetopray.databinding.PrayTimeCardBinding
import com.example.timetopray.ui.data.models.praytimes.CustomPrayTime
import com.example.timetopray.ui.data.models.praytimes.PrayerTime

class MainFragmentAdapter: RecyclerView.Adapter<MainFragmentViewHolder>() {

    private var listOfPrayTime = emptyList<CustomPrayTime>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val binding = PrayTimeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.bind(listOfPrayTime[position])
    }

    override fun getItemCount() = listOfPrayTime.size

    fun setList(listOfPrayTime: List<CustomPrayTime>){
        this.listOfPrayTime = listOfPrayTime
    }
}