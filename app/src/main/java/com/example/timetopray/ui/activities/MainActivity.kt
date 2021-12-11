package com.example.timetopray.ui.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.timetopray.R
import com.example.timetopray.databinding.ActivityMainBinding
import com.example.timetopray.ui.data.models.userlocation.UserLocation
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

    fun setUserPermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        getLocation()
                    }

                    if (report.deniedPermissionResponses.size != 0 && !report.isAnyPermissionPermanentlyDenied){
                        setUserPermissions()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showSettingsDialog()
            return
        } else {
            getLocation()
        }
    }

    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Konum İzini Gerekiyor")
        builder.setMessage("Uygulama namaz vakitlerini alabilmek için konumunuza ihtiyaç duymaktadır, İzinler -> Konuma gidip izin vermeniz gerekmektedir.")
        builder.setPositiveButton(
            "Ayarlara git"
        ) { dialog, which ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, 111)
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun getLocation() {
        Utils.getLocation(this) {
            val userLocation =
                it?.let { location ->
                    UserLocation(
                        location.adminArea.uppercase(),
                        it.subAdminArea
                    )
                }
            if (userLocation != null) {
                mTimeToPrayViewModel.insertUserLocation(userLocation)
                mTimeToPrayViewModel.getAllCities(userLocation.cityName)
            }
        }
    }
}
