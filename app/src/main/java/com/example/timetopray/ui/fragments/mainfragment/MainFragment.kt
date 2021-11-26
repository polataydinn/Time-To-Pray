package com.example.timetopray.ui.fragments.mainfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.timetopray.R
import com.example.timetopray.databinding.FragmentMainBinding
import com.example.timetopray.ui.data.viewmodel.TimeToPrayViewModel
import com.example.timetopray.ui.fragments.qiblefragment.QiblaFragment
import java.sql.Time

class MainFragment : Fragment() {
    private lateinit var _binding: FragmentMainBinding
    private val binding get() = _binding
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTimeToPrayViewModel.getAllCities("KAHRAMANMARAŞ")


        binding.mainFragmentCompass.setOnClickListener {
            val qiblaFragment = QiblaFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, qiblaFragment)?.addToBackStack("")?.commit()
        }
    }
}