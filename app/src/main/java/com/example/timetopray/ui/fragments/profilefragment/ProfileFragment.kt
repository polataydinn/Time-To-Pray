package com.example.timetopray.ui.fragments.profilefragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.timetopray.R
import com.example.timetopray.databinding.FragmentProfileBinding
import com.example.timetopray.ui.activities.MainActivity
import com.example.timetopray.ui.data.models.ayats.Ayat
import com.google.android.play.core.review.ReviewManagerFactory
import kotlin.system.exitProcess


class ProfileFragment : Fragment() {
    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileFragmentReview.setOnClickListener {
            inAppReview()
        }

        binding.profileFragmentShare.setOnClickListener {
            startShareIntent("https://play.google.com/store/apps/details?id=com.pozetech.timetopray")
        }

        binding.profileFragmentAbout.setOnClickListener {
            showDialog()
        }

        binding.fragmentProfileExit.setOnClickListener {
            exitProcess(-1)
        }
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder((activity as MainActivity))
        builder.setTitle(R.string.about)
        builder.setMessage(R.string.about_text)
        builder.setCancelable(true)
        builder.show()
    }

    private fun inAppReview() {
        val reviewManager = ReviewManagerFactory.create((activity as MainActivity))
        val requestReviewFlow = reviewManager.requestReviewFlow()
        requestReviewFlow.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                val reviewInfo = request.result
                val flow = reviewManager.launchReviewFlow((activity as MainActivity), reviewInfo)
                flow.addOnCompleteListener {

                }
            } else {
                Log.d("Error: ", request.exception.toString())
            }
        }
    }

    fun startShareIntent(appUrl: String) {
        val intent = Intent(Intent.ACTION_SEND);
        intent.type = "text/plain";
        intent.putExtra(Intent.EXTRA_TEXT, appUrl)
        startActivity(Intent.createChooser(intent, "Uygulamayı Paylaş"))
    }
}