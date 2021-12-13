package com.example.timetopray.ui.fragments.fridaymessagesfragment.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.timetopray.databinding.FirdayMessagesCardBinding
import com.example.timetopray.ui.data.models.fridaymessages.FridayMessageItem

class FridayMessagesViewHolder(private val binding: FirdayMessagesCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(fridayMessage: FridayMessageItem) {
        Glide
            .with(binding.root)
            .load(fridayMessage.image)
            .centerCrop()
            .into(binding.fridayMessagesImage)
    }

}
