package com.example.timetopray.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.timetopray.R
import com.example.timetopray.databinding.ActivityMainBinding
import com.example.timetopray.ui.constants.Constants
import com.example.timetopray.ui.data.models.userlocation.UserLocation
import com.example.timetopray.ui.fragments.fridaymessagesfragment.FridayMessagesFragment
import com.example.timetopray.ui.fragments.mainfragment.MainFragment
import com.example.timetopray.ui.fragments.profilefragment.ProfileFragment
import com.example.timetopray.ui.util.Utils
import com.example.timetopray.ui.viewmodel.TimeToPrayViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import github.com.st235.lib_expandablebottombar.Menu
import github.com.st235.lib_expandablebottombar.MenuItemDescriptor
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()
    var mInterstitialAd: InterstitialAd? = null
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomBar: ExpandableBottomBar = binding.bottomBar
        val menu = bottomBar.menu
        val adRequest = AdRequest.Builder().build()
        binding.bannerAd.loadAd(adRequest)

        InterstitialAd.load(
            this,
            Constants.AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("MainActivity", "Hata")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.show(this@MainActivity)
                }
            })

        MobileAds.initialize(this) { }
        setMenu(menu)
        bottomBarListener(bottomBar)
        setUserPermissions()
        checkIfAyatsDownloaded()
        checkIfFridayMessagesDownloaded()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ifPermissionsDenied()) {
            showSettingsDialog()
            return
        } else {
            getLocation()
        }
    }

    fun setMenu(menu: Menu) {
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
    }

    fun bottomBarListener(bottomBar: ExpandableBottomBar) {
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
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        getLocation()
                    }

                    if (report.deniedPermissionResponses.size != 0 && !report.isAnyPermissionPermanentlyDenied) {
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
            var mAddress = it
            if (it?.adminArea?.contains('i')!!) {
                mAddress?.adminArea = it.adminArea?.replace('i', 'İ')
            }
            mTimeToPrayViewModel.getUserLocation?.observe(this) { mUserLocation ->
                if (mUserLocation == null && mAddress != null) {
                    insertUserLocationToRoom(mAddress)
                } else if (mAddress != null && (mUserLocation.cityName != mAddress.adminArea?.uppercase())) {
                    mTimeToPrayViewModel.deleteUserLocation()
                    mTimeToPrayViewModel.deleteAllTimes()
                    mTimeToPrayViewModel.deleteAllCityInfo()
                    insertUserLocationToRoom(mAddress)
                }
            }
        }
    }

    private fun insertUserLocationToRoom(location: Address) {
        val userLocation =
            location.let { mLocation ->
                UserLocation(
                    mLocation.adminArea.uppercase(),
                    mLocation.subAdminArea
                )
            }
        if (userLocation != null) {
            mTimeToPrayViewModel.insertUserLocation(userLocation)
            mTimeToPrayViewModel.getAllCities(userLocation.cityName)
        }
    }

    fun ifPermissionsDenied(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun checkIfAyatsDownloaded() {
        mTimeToPrayViewModel.getAllAyats?.observe(this) {
            if (it.isEmpty()) {
                mTimeToPrayViewModel.getAllAyats()
            }
        }
    }

    private fun checkIfFridayMessagesDownloaded() {
        mTimeToPrayViewModel.getAllFridayMessages.observe(this) {
            if (it.isEmpty()) {
                mTimeToPrayViewModel.getAllMessages()
            }
        }
    }
}
