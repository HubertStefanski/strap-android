package com.hstefans.strap_android.handlers

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hstefans.strap_android.fragments.TaskFragment
import com.hstefans.strap_android.fragments.UserSettingsFragment
import com.hstefans.strap_android.fragments.WaypointsFragment

@Suppress("DEPRECATION")
internal class MyAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                UserSettingsFragment()
            }
            1 -> {
                TaskFragment()
            }
            2 -> {
                WaypointsFragment()
            }
            3 -> {
                com.hstefans.strap_android.fragments.ReportFragment()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}