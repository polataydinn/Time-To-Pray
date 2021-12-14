package com.example.timetopray.ui.fragments.ayats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.timetopray.databinding.AyatsCardBinding
import com.example.timetopray.ui.data.models.ayats.Ayat

class AyatsAdapter(val onItemClickListener: (Ayat, View) -> Unit) : RecyclerView.Adapter<AyatsViewHolder>() {
    var listOfAyats = listOf<Ayat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatsViewHolder {
        val binding = AyatsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AyatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AyatsViewHolder, position: Int) {
        holder.bind(listOfAyats[position], onItemClickListener)
    }

    override fun getItemCount() = listOfAyats.size

    fun setList(listOfAyats: List<Ayat>) {
        this.listOfAyats = listOfAyats
    }
}