package com.example.timetopray.ui.fragments.fridaymessagesfragment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.timetopray.databinding.FragmentFridayMessagesBinding
import com.example.timetopray.ui.fragments.fridaymessagesfragment.adapter.FridayMessagesAdapter
import com.example.timetopray.ui.viewmodel.TimeToPrayViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FridayMessagesFragment : Fragment() {
    private lateinit var _binding: FragmentFridayMessagesBinding
    private val binding get() = _binding
    private lateinit var adapter: FridayMessagesAdapter
    private val mTimeToPrayViewModel: TimeToPrayViewModel by viewModels()


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
        adapter = FridayMessagesAdapter { drawable, clickedItem ->
            when (clickedItem) {
                0 -> {
                    saveImage(drawable)
                }
                1 -> {

                }
            }
        }

        mTimeToPrayViewModel.getAllFridayMessages?.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setList(it)
                binding.fridayMessagesRecyclerView.adapter = adapter
            }
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


}