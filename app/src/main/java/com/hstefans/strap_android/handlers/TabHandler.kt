package com.hstefans.strap_android.handlers

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ReportFragment
import com.hstefans.strap_android.TaskFragment
import com.hstefans.strap_android.UserSettingsFragment
import com.hstefans.strap_android.WaypointsFragment

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
                com.hstefans.strap_android.ReportFragment()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}