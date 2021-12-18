package com.example.timetopray.ui.fragments.fridaymessagesfragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.timetopray.databinding.FragmentFridayMessagesBinding
import com.example.timetopray.ui.activities.MainActivity
import com.example.timetopray.ui.constants.Constants
import com.example.timetopray.ui.fragments.fridaymessagesfragment.adapter.FridayMessagesAdapter
import com.example.timetopray.ui.viewmodel.TimeToPrayViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FridayMessagesFragment : Fragment() {
    private lateinit var mainActivity: MainActivity
    private lateinit var _binding: FragmentFridayMessagesBinding
    private val binding get() = _binding
    private lateinit var adapter: FridayMessagesAdapter
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()
    private var savedFile: File? = null
    var listOfSavedFiles = mutableListOf<File>()
    var mInterstitialAd: InterstitialAd? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFridayMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        setAdInstance()

        adapter = FridayMessagesAdapter { drawable, clickedItem ->
            when (clickedItem) {
                0 -> {
                    saveImage(drawable)
                }
                1 -> {
                    loadAdAndStartIntent(drawable)
                }
            }
        }

        mTimeToPrayViewModel.getAllFridayMessages.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setList(it.shuffled())
                binding.fridayMessagesRecyclerView.adapter = adapter
            }
        }
    }

    private fun setAdInstance() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            mainActivity,
            Constants.AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("MainActivity", "Hata")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun loadAdAndStartIntent(drawable: Drawable) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(mainActivity)
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        shareIntent(drawable)
                    }
                }
        } else {
            shareIntent(drawable)
        }
    }

    private fun shareIntent(drawable: Drawable) {
        saveImage(drawable)
        savedFile?.let {
            val intent = Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(savedFile!!.absolutePath))
            listOfSavedFiles.add(savedFile!!)
            startActivity(Intent.createChooser(intent, "Resimi Paylaş"))
        }
    }


    private fun saveImage(drawable: Drawable) {
        val file = getDisc()

        if (!file.exists() && !file.mkdirs()) {
            file.mkdir()
        }

        val simpleDateFormat = SimpleDateFormat("yyyymmsshhmmss")
        val date = simpleDateFormat.format(Date())
        val name = "IMG" + date + ".jpg"
        val fileName = file.absolutePath + "/" + name
        val newFile = File(fileName)

        try {
            val draw = drawable as BitmapDrawable
            val bitmap = draw.bitmap
            val fileOutPutStream = FileOutputStream(newFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutPutStream)
            Toast.makeText(requireContext(), "Dosya Basariyla Kaydedildi", Toast.LENGTH_SHORT)
                .show()
            savedFile = newFile
            fileOutPutStream.flush()
            fileOutPutStream.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun getDisc(): File {
        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File(file, "FridayMessages")
    }

    override fun onDestroy() {
        listOfSavedFiles.forEach {
            it.delete()
        }
        super.onDestroy()
    }
}