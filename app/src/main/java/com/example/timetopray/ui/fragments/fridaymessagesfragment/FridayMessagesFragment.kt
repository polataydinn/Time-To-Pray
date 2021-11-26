package com.example.timetopray.ui.fragments.fridaymessagesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timetopray.databinding.FragmentFridayMessagesBinding

class FridayMessagesFragment : Fragment() {
    private lateinit var _binding: FragmentFridayMessagesBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFridayMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }
}