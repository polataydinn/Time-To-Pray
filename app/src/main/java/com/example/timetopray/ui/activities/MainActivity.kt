package com.example.timetopray.ui.activities

import android.graphics.Color
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timetopray.R
import com.example.timetopray.databinding.ActivityMainBinding
import com.example.timetopray.ui.fragments.LoginFragment
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import github.com.st235.lib_expandablebottombar.MenuItemDescriptor

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                R.id.questions,
                R.drawable.ic_question,
                R.string.questions,
                resources.getColor(R.color.turquoise)
            ).build()
        )

        menu.add(
            MenuItemDescriptor.Builder(
                this,
                R.id.settings,
                R.drawable.ic_settings,
                R.string.settings,
                resources.getColor(R.color.turquoise)
            ).build()
        )

        bottomBar.onItemSelectedListener = { view, menuItem, _ ->
            /**
             * handle menu item clicks here,
             * but clicks on already selected item will not affect this callback
             */
        }

        bottomBar.onItemReselectedListener = { view, menuItem, _ ->
            /**
             * handle here all the click in already selected items
             */
        }

    }
}