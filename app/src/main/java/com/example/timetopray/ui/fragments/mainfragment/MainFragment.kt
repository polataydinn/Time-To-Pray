package com.example.timetopray.ui.fragments.mainfragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.timetopray.R
import com.example.timetopray.databinding.FragmentMainBinding
import com.example.timetopray.ui.constants.Constants
import com.example.timetopray.ui.data.models.praytimes.CustomPrayTime
import com.example.timetopray.ui.data.viewmodel.TimeToPrayViewModel
import com.example.timetopray.ui.fragments.mainfragment.adapter.MainFragmentAdapter
import com.example.timetopray.ui.fragments.qiblefragment.QiblaFragment
import java.sql.Time
import java.util.*

class MainFragment : Fragment() {
    private lateinit var _binding: FragmentMainBinding
    private val binding get() = _binding
    private val mainFragmentAdapter = MainFragmentAdapter()
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentTime = Calendar.getInstance().time
        binding.mainFragmentDate.text =
            currentTime.date.toString() + " " + Constants.MONTHS_HASHMAP[currentTime.month + 1] + " " + (currentTime.year + 1900)

        binding.mainFragmentCurrentTime.format12Hour = "kk:mm"

        mTimeToPrayViewModel.getAllCities("KAHRAMANMARAŞ")

        mTimeToPrayViewModel.getAllTimes?.observe(viewLifecycleOwner) {
            val listOfCustomPrayTime: MutableList<CustomPrayTime> = mutableListOf()
            it[0].let { prayerTime ->
                prayerTime.apply {
                    listOfCustomPrayTime.add(CustomPrayTime("İmsak", imsak))
                    listOfCustomPrayTime.add(CustomPrayTime("Güneş", gunes))
                    listOfCustomPrayTime.add(CustomPrayTime("Öğle", ogle))
                    listOfCustomPrayTime.add(CustomPrayTime("İkindi", ikindi))
                    listOfCustomPrayTime.add(CustomPrayTime("Akşam", aksam))
                    listOfCustomPrayTime.add(CustomPrayTime("Yatsı", yatsi))
                }
            }
            mainFragmentAdapter.setList(listOfCustomPrayTime)
            binding.prayTimeRv.adapter = mainFragmentAdapter
        }

        mTimeToPrayViewModel.getCity?.observe(viewLifecycleOwner) {
            it.let {city ->
                binding.mainFragmentCityName.text = city.name
            }
        }

        binding.mainFragmentCompass.setOnClickListener {
            val qiblaFragment = QiblaFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, qiblaFragment)?.addToBackStack("")?.commit()
        }
    }
}