package com.example.timetopray.ui.fragments.ayats

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.timetopray.R
import com.example.timetopray.databinding.FragmentAyatsBinding
import com.example.timetopray.ui.activities.MainActivity
import com.example.timetopray.ui.data.models.ayats.Ayat
import com.example.timetopray.ui.fragments.ayats.adapter.AyatsAdapter
import com.example.timetopray.ui.fragments.mainfragment.MainFragment
import com.example.timetopray.ui.viewmodel.TimeToPrayViewModel

class AyatsFragment : Fragment() {
    private lateinit var _binding: FragmentAyatsBinding
    private val binding get() = _binding
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()
    private lateinit var adapter: AyatsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAyatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomBar()
        adapter = AyatsAdapter { ayat, view ->
            popUpMenu(view, ayat)
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showBottomBar()
    }

    fun createSwipeGesture(context: Context, recyclerView: RecyclerView) {
        val swipeGesture = object : SwipeGesture(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val ayat = adapter.listOfAyats[viewHolder.absoluteAdapterPosition]
                        recyclerView.adapter?.notifyItemChanged(viewHolder.absoluteAdapterPosition)
                        startShareIntent(ayat)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun popUpMenu(view: View, ayat: Ayat) {
        val popUpMenu = PopupMenu(requireContext(), view, Gravity.END)
        popUpMenu.inflate(R.menu.ayats_menu)
        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_share -> {
                    startShareIntent(ayat)
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
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)

    }

    fun startShareIntent(ayat: Ayat) {
        val intent = Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, ayat.text + " - " + ayat.source)
        startActivity(Intent.createChooser(intent, "Ayeti Paylaş"))
    }

    fun copyAyatToClipboard(ayat: Ayat) {
        val clipboardManager =
            (activity as MainActivity).getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Ayat", ayat.text + " - " + ayat.source)
        clipboardManager.setPrimaryClip(clip)
        clip.description

        Toast.makeText(requireContext(), "Ayet Kopyalandı", Toast.LENGTH_SHORT).show()
    }
}