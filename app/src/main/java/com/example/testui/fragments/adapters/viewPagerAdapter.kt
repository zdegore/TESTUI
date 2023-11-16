package com.example.testui.fragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testui.fragments.FuelDataFragment
import com.example.testui.fragments.Monitor1Fragment
import com.example.testui.fragments.Monitor2Fragment

class viewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle)
{
    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            FuelDataFragment()

        else if (position == 1)
            Monitor1Fragment()

        else
            Monitor2Fragment()
    }

    override fun getItemCount(): Int {
        return 3
    }
}