package com.example.testui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testui.databinding.ActivityMainBinding
import com.example.testui.fragments.Monitor1Fragment
import com.example.testui.fragments.FuelDataFragment
import com.example.testui.fragments.adapters.viewPagerAdapter


class MainActivity : AppCompatActivity(){


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpTabs()
    }
    private fun setUpTabs(){

        val adapter = viewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Monitor1Fragment(), "Monitor")
        adapter.addFragment(FuelDataFragment(), "Fuel Data")

        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        binding.tabs.getTabAt(0)!!.setIcon(R.drawable.monitor)
        binding.tabs.getTabAt(1)!!.setIcon(R.drawable.baseline_thermostat_24)


    }


}