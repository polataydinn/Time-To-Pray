package com.example.timetopray.ui.fragments.ayats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.timetopray.databinding.AyatsCardBinding
import com.example.timetopray.ui.data.models.ayats.Ayat

class AyatsAdapter : RecyclerView.Adapter<AyatsViewHolder>() {
    private var listOfAyats = listOf<Ayat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatsViewHolder {
        val binding = AyatsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AyatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AyatsViewHolder, position: Int) {
        holder.bind(listOfAyats[position])
    }

    override fun getItemCount() = listOfAyats.size

    fun setList(listOfAyats: List<Ayat>) {
        this.listOfAyats = listOfAyats
    }
}