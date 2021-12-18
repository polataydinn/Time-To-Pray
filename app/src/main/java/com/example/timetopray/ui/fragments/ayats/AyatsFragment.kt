package com.example.timetopray.ui.fragments.ayats

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder
import com.example.timetopray.R
import com.example.timetopray.databinding.FragmentAyatsBinding
import com.example.timetopray.ui.activities.MainActivity
import com.example.timetopray.ui.constants.Constants
import com.example.timetopray.ui.data.models.ayats.Ayat
import com.example.timetopray.ui.fragments.ayats.adapter.AyatsAdapter
import com.example.timetopray.ui.fragments.mainfragment.MainFragment
import com.example.timetopray.ui.viewmodel.TimeToPrayViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AyatsFragment : Fragment() {
    private lateinit var _binding: FragmentAyatsBinding
    private val binding get() = _binding
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()
    private lateinit var adapter: AyatsAdapter
    private lateinit var mainActivity: MainActivity
    var mInterstitialAd: InterstitialAd? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAyatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        mainActivity.hideBottomBar()

        setAdInstance()

        adapter = AyatsAdapter { ayat, view ->
            popUpMenu(view, ayat)
        }

        binding.ayatsFragmentRecyclerView.viewTreeObserver.addOnGlobalLayoutListener {
            val ayatCardView = binding.ayatsFragmentRecyclerView.findViewHolderForAdapterPosition(0)
            if (ayatCardView != null) showBubbleCase(ayatCardView)
        }



        mTimeToPrayViewModel.getAllAyats?.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setList(it.shuffled())
                binding.ayatsFragmentRecyclerView.adapter = adapter
            }
        }


        binding.ayatsFragmentBackPress.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, MainFragment())?.commit()
        }

        createSwipeGesture(requireContext(), binding.ayatsFragmentRecyclerView)
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStop() {
        super.onStop()
        mainActivity.showBottomBar()
    }

    fun createSwipeGesture(context: Context, recyclerView: RecyclerView) {
        val swipeGesture = object : SwipeGesture(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        loadAdAndShowIntent(recyclerView, viewHolder)
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun loadAdAndShowIntent(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(mainActivity)
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        onItemSwipped(recyclerView, viewHolder)
                        mInterstitialAd = null
                    }
                }
        }else{
            onItemSwipped(recyclerView, viewHolder)
        }
    }

    private fun onItemSwipped(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val ayat =
            adapter.listOfAyats[viewHolder.absoluteAdapterPosition]
        recyclerView.adapter?.notifyItemChanged(viewHolder.absoluteAdapterPosition)
        startShareIntent(ayat)
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun popUpMenu(view: View, ayat: Ayat) {
        val popUpMenu = PopupMenu(requireContext(), view, Gravity.END)
        popUpMenu.inflate(R.menu.ayats_menu)
        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_share -> {
                    if (mInterstitialAd != null) {

                        mInterstitialAd?.show(mainActivity)
                        mInterstitialAd?.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdDismissedFullScreenContent() {
                                    startShareIntent(ayat)
                                }
                            }
                    }
                    else{
                        startShareIntent(ayat)
                    }
                    return@setOnMenuItemClickListener true
                }
                R.id.menu_copy -> {
                    copyAyatToClipboard(ayat)
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener true
            }
        }
        popUpMenu.show()
        val popUp = PopupMenu::class.java.getDeclaredField("mPopup")
        popUp.isAccessible = true
        val menu = popUp.get(popUpMenu)
        menu.javaClass.getDeclaredMethod(
            "setForceShowIcon", Boolean::
            class.java
        )
            .invoke(menu, true)

    }

    private fun showBubbleCase(ayatCardView: RecyclerView.ViewHolder) {
        BubbleShowCaseBuilder(mainActivity)
            .title("Paylaşma")
            .description("Ayetlerı yana kaydırarak yada üstüne basılı tutarak, paylaşabilirsiniz")
            .backgroundColor(ContextCompat.getColor(requireContext(), R.color.turquoise))
            .image(ContextCompat.getDrawable(requireContext(), R.drawable.ic_book_white)!!)
            .closeActionImage(ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)!!)
            .textColor(ContextCompat.getColor(requireContext(), R.color.white))
            .targetView(ayatCardView.itemView.rootView.findViewById(R.id.ayats_card_root))
            .showOnce("SHOW_ONCE")
            .show()
    }

    fun startShareIntent(ayat: Ayat) {
        val intent = Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, ayat.text + " - " + ayat.source)
        startActivity(Intent.createChooser(intent, "Ayeti Paylaş"))
    }

    fun copyAyatToClipboard(ayat: Ayat) {
        val clipboardManager =
            mainActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Ayat", ayat.text + " - " + ayat.source)
        clipboardManager.setPrimaryClip(clip)
        clip.description

        Toast.makeText(requireContext(), "Ayet Kopyalandı", Toast.LENGTH_SHORT).show()
    }
}