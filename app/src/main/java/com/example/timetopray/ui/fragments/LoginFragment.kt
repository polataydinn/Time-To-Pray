package com.example.timetopray.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timetopray.R
import com.example.timetopray.databinding.FragmentAnswerQuestionBinding
import com.example.timetopray.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
}