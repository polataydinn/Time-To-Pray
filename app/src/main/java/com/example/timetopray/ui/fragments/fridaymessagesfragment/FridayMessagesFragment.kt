package com.example.timetopray.ui.fragments.fridaymessagesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.timetopray.databinding.FragmentFridayMessagesBinding
import com.example.timetopray.ui.fragments.fridaymessagesfragment.adapter.FridayMessagesAdapter
import com.example.timetopray.ui.viewmodel.TimeToPrayViewModel

class FridayMessagesFragment : Fragment() {
    private lateinit var _binding: FragmentFridayMessagesBinding
    private val binding get() = _binding
    private lateinit var adapter: FridayMessagesAdapter
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFridayMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FridayMessagesAdapter()
        mTimeToPrayViewModel.getAllMessages()

        mTimeToPrayViewModel.getAllFridayMessages?.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                adapter.setList(it)
                binding.fridayMessagesRecyclerView.adapter = adapter
            }
        }
    }
}