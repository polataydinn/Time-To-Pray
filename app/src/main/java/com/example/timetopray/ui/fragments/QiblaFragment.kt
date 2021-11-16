package com.example.timetopray.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timetopray.R
import com.example.timetopray.databinding.FragmentAnswerQuestionBinding
import com.example.timetopray.databinding.FragmentQiblaBinding
import com.example.timetopray.ui.activities.MainActivity

class QiblaFragment : Fragment() {
    private lateinit var _binding: FragmentQiblaBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQiblaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).hideBottomBar()

        binding.qibleFragmentBackPress.setOnClickListener{
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFragmentContainer, MainFragment())
                    .commit()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showBottomBar()
    }
}