package com.example.timetopray.ui.fragments.mainfragment.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.timetopray.databinding.PrayTimeCardBinding
import com.example.timetopray.ui.data.models.praytimes.CustomPrayTime

class MainFragmentViewHolder(val binding: PrayTimeCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(prayerTime: CustomPrayTime) {
        binding.prayText.text = prayerTime.name
        binding.prayTime.text = prayerTime.time
    }
}
