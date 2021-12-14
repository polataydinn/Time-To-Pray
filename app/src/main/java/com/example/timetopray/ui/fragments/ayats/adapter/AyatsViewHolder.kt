package com.example.timetopray.ui.fragments.ayats.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.timetopray.databinding.AyatsCardBinding
import com.example.timetopray.ui.data.models.ayats.Ayat

class AyatsViewHolder(private val binding: AyatsCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ayat: Ayat) {
        binding.ayatsFragmentAyatText.text = ayat.text
        binding.ayatsFragmentAyatSource.text = ayat.source
    }

}
