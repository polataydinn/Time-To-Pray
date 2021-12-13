package com.example.timetopray.ui.fragments.fridaymessagesfragment.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.timetopray.databinding.FirdayMessagesCardBinding
import com.example.timetopray.ui.data.models.fridaymessages.FridayMessageItem

class FridayMessagesAdapter(val onItemClickListener: (Drawable, Int) -> Unit) :
    RecyclerView.Adapter<FridayMessagesViewHolder>() {
    private var listOfFridayMessages = emptyList<FridayMessageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FridayMessagesViewHolder {
        val binding =
            FirdayMessagesCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FridayMessagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FridayMessagesViewHolder, position: Int) {
        holder.bind(listOfFridayMessages[position], onItemClickListener)
    }

    override fun getItemCount() = listOfFridayMessages.size

    fun setList(listOfFridayMessages: List<FridayMessageItem>) {
        this.listOfFridayMessages = listOfFridayMessages
    }
}