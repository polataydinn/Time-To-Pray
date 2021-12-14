package com.example.timetopray.ui.fragments.ayats

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.timetopray.R
import com.example.timetopray.databinding.FragmentAyatsBinding
import com.example.timetopray.ui.activities.MainActivity
import com.example.timetopray.ui.fragments.ayats.adapter.AyatsAdapter
import com.example.timetopray.ui.fragments.mainfragment.MainFragment
import com.example.timetopray.ui.viewmodel.TimeToPrayViewModel

class AyatsFragment : Fragment() {
    private lateinit var _binding: FragmentAyatsBinding
    private val binding get() = _binding
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()
    private lateinit var adapter: AyatsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAyatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomBar()
        adapter = AyatsAdapter()

        mTimeToPrayViewModel.getAllAyats?.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setList(it.shuffled())
                binding.ayatsFragmentRecyclerView.adapter = adapter
            }
        }

        binding.ayatsFragmentBackPress.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, MainFragment())?.commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showBottomBar()
    }
}