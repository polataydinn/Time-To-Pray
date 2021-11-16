package com.example.timetopray.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.timetopray.R
import com.example.timetopray.databinding.ActivityMainBinding
import com.example.timetopray.ui.fragments.ForumFragment
import com.example.timetopray.ui.fragments.MainFragment
import com.example.timetopray.ui.fragments.ProfileFragment
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
            when (menuItem.id) {
                R.id.home -> {
                    val mainFragment = MainFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, mainFragment)
                        .commit()
                }
                R.id.questions -> {
                    val forumFragment = ForumFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, forumFragment)
                        .commit()
                }
                R.id.settings -> {
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