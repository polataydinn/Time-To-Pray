package com.example.timetopray.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.timetopray.R
import com.example.timetopray.databinding.ActivityMainBinding
import com.example.timetopray.ui.fragments.fridaymessagesfragment.FridayMessagesFragment
import com.example.timetopray.ui.fragments.mainfragment.MainFragment
import com.example.timetopray.ui.fragments.profilefragment.ProfileFragment
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
}