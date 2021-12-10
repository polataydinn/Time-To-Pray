package com.example.timetopray.ui.activities

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.timetopray.R
import com.example.timetopray.databinding.ActivityMainBinding
import com.example.timetopray.ui.data.viewmodel.TimeToPrayViewModel
import com.example.timetopray.ui.fragments.fridaymessagesfragment.FridayMessagesFragment
import com.example.timetopray.ui.fragments.mainfragment.MainFragment
import com.example.timetopray.ui.fragments.profilefragment.ProfileFragment
import com.example.timetopray.ui.util.Utils
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import github.com.st235.lib_expandablebottombar.MenuItemDescriptor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUserPermissions()

        Utils.getDetailedLocation(this){
            mTimeToPrayViewModel.detailedLocation?.postValue(it)
        }

        Utils.getLocation(this){
            val liveData = mutableListOf<String>()
            liveData.add(it)
            mTimeToPrayViewModel.location?.postValue(it)
        }

        val bottomBar: ExpandableBottomBar = binding.bottomBar
        val menu = bottomBar.menu

        menu.add(
            MenuItemDescriptor.Builder(
                this,
                R.id.home,
                R.drawable.ic_home,
                R.string.home,
                resources.getColor(R.color.turquoise)
            ).build()
        )

        menu.add(
            MenuItemDescriptor.Builder(
                this,
                R.id.friday_messages,
                R.drawable.ic_question,
                R.string.friday_messages,
                resources.getColor(R.color.turquoise)
            ).build()
        )

        menu.add(
            MenuItemDescriptor.Builder(
                this,
                R.id.profile,
                R.drawable.ic_profile,
                R.string.profile,
                resources.getColor(R.color.turquoise)
            ).build()
        )

        bottomBar.onItemSelectedListener = { _, menuItem, _ ->
            when (menuItem.id) {
                R.id.home -> {
                    val mainFragment = MainFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, mainFragment)
                        .commit()
                }
                R.id.friday_messages -> {
                    val forumFragment = FridayMessagesFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, forumFragment)
                        .commit()
                }
                R.id.profile -> {
                    val profileFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, profileFragment)
                        .commit()
                }
            }
        }
    }

    fun hideBottomBar() {
        binding.bottomBar.isVisible = false
    }

    fun showBottomBar() {
        binding.bottomBar.isVisible = true
    }

    private fun setUserPermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        Utils.getLocation(this@MainActivity){
                            println("breakpoint")
                        }
                    }

                    if (report.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(
                    baseContext,
                    "Hata! ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .onSameThread()
            .check()
    }

    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(baseContext)
        builder.setTitle("Konum İzini Gerekiyor")
        builder.setMessage("Uygulama namaz vakitlerini alabilmek için konumunuza ihtiyaç duymaktadır.")
        builder.setPositiveButton(
            "Ayarlara git"
        ) { dialog, which ->
            dialog.cancel()
        }
        builder.setNegativeButton(
            "İptal et"
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }
}
