package com.example.timetopray.ui.fragments.mainfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.timetopray.R
import com.example.timetopray.databinding.FragmentMainBinding
import com.example.timetopray.ui.activities.MainActivity
import com.example.timetopray.ui.constants.Constants
import com.example.timetopray.ui.data.models.praytimes.CustomPrayTime
import com.example.timetopray.ui.fragments.ayats.AyatsFragment
import com.example.timetopray.ui.fragments.mainfragment.adapter.MainFragmentAdapter
import com.example.timetopray.ui.fragments.qiblefragment.QiblaFragment
import com.example.timetopray.ui.util.Utils
import com.example.timetopray.ui.viewmodel.TimeToPrayViewModel
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

        mTimeToPrayViewModel.getUserLocation?.observe(viewLifecycleOwner) {
            it?.let {
                binding.mainFragmentCityName.text = it.cityName
                binding.detailedAddress.text = it.detailedAddress
            }
        }

        mTimeToPrayViewModel.getAllTimes?.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val listOfCustomPrayTime: MutableList<CustomPrayTime> = mutableListOf()
                val prayerTime = it.filter {
                    it.date == ((currentTime.year + 1900).toString() + "-" + (currentTime.month + 1) + "-" + (currentTime.date))
                }
                if (prayerTime.isNotEmpty()) {
                    prayerTime[0].let { mPrayerTime ->
                        mPrayerTime.apply {
                            listOfCustomPrayTime.add(CustomPrayTime("??msak", imsak))
                            listOfCustomPrayTime.add(CustomPrayTime("G??ne??", gunes))
                            listOfCustomPrayTime.add(CustomPrayTime("????le", ogle))
                            listOfCustomPrayTime.add(CustomPrayTime("??kindi", ikindi))
                            listOfCustomPrayTime.add(CustomPrayTime("Ak??am", aksam))
                            listOfCustomPrayTime.add(CustomPrayTime("Yats??", yatsi))
                        }
                    }
                }

                mainFragmentAdapter.setList(listOfCustomPrayTime)
                binding.prayTimeRv.adapter = mainFragmentAdapter
            } else {
                Utils.getLocation(activity as MainActivity) { cityName ->
                    cityName?.adminArea?.let { city -> mTimeToPrayViewModel.getAllCities(city.uppercase()) }
                }
            }
        }

        mTimeToPrayViewModel.getCity?.observe(viewLifecycleOwner) {
            it?.let { city ->
                binding.mainFragmentCityName.text = city.name
            }
        }

        binding.mainFragmentCompass.setOnClickListener {
            val qiblaFragment = QiblaFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, qiblaFragment)?.addToBackStack("")?.commit()
        }

        binding.mainFragmentAyats.setOnClickListener {
            val ayatsFragment = AyatsFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, ayatsFragment)?.addToBackStack(".")?.commit()
        }
    }
}