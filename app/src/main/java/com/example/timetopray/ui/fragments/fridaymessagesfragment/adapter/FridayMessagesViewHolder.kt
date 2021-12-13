package com.example.timetopray.ui.fragments.fridaymessagesfragment.adapter

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.timetopray.databinding.FirdayMessagesCardBinding
import com.example.timetopray.ui.data.models.fridaymessages.FridayMessageItem

class FridayMessagesViewHolder(private val binding: FirdayMessagesCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        fridayMessage: FridayMessageItem,
        onItemClickListener: (Drawable, Int) -> Unit
    ) {
        Glide
            .with(binding.root)
            .load(fridayMessage.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.fridayMessagesImage)

        binding.fridayMessagesImage.drawable

        binding.fridayMessagesDownload.setOnClickListener {
            onItemClickListener(binding.fridayMessagesImage.drawable, 0)
        }

        binding.fridayMessagesShare.setOnClickListener {
            onItemClickListener(binding.fridayMessagesImage.drawable, 1)
        }
    }
}
