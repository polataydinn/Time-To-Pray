package com.example.timetopray.ui.fragments.qiblefragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.timetopray.R
import com.example.timetopray.databinding.FragmentQiblaBinding
import com.example.timetopray.ui.activities.MainActivity
import com.example.timetopray.ui.fragments.mainfragment.MainFragment
import com.example.timetopray.ui.viewmodel.TimeToPrayViewModel

class QiblaFragment : Fragment(), SensorEventListener {
    private lateinit var _binding: FragmentQiblaBinding
    private val binding get() = _binding
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()
    private lateinit var sensorManager: SensorManager
    private var currentDegree: Float = 0.0f
    private lateinit var adBackground: FrameLayout

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQiblaBinding.inflate(inflater, container, false)
        (activity as MainActivity).window.statusBarColor =
            (activity as MainActivity).getColor(R.color.dark_turquoise)
        adBackground = (activity as MainActivity).findViewById(R.id.activity_ad_background)
        adBackground.setBackgroundColor((activity as MainActivity).getColor(R.color.dark_turquoise))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomBar()
        binding.qibleFragmentClock.format12Hour = "kk:mm"

        sensorManager =
            (activity as MainActivity).getSystemService(Context.SENSOR_SERVICE) as SensorManager

        binding.qibleFragmentBackPress.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, MainFragment())?.commit()
        }

        mTimeToPrayViewModel.getUserLocation?.observe(viewLifecycleOwner) {
            it?.let {
                binding.qibleFragmentCityName.text = it.cityName
                binding.detailedAddress.text = it.detailedAddress
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showBottomBar()
        (activity as MainActivity).window.statusBarColor =
            (activity as MainActivity).getColor(R.color.white)
        adBackground.setBackgroundColor((activity as MainActivity).getColor(R.color.white))
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var degree = Math.round(event?.values!![0])
        val animation = RotateAnimation(
            currentDegree,
            (-degree).toFloat(), Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        animation.apply {
            duration = 120
            fillAfter = true
        }
        binding.qibleCompass.startAnimation(animation)
        currentDegree = (-degree).toFloat()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

}