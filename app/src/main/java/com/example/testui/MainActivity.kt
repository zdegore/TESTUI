package com.example.testui

import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.os.Handler

import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.testui.databinding.ActivityMainBinding
import com.example.testui.fragments.Monitor1Fragment
import com.example.testui.fragments.FuelDataFragment
import com.example.testui.fragments.adapters.viewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import java.time.LocalDate
import java.util.Calendar


class MainActivity : AppCompatActivity(){

    private val handler = Handler(Looper.getMainLooper())
    private val updateIntervalMillis: Long = 60000 // 1 second

    private val updateRunnable = object : Runnable {
        override fun run() {
            // Update your view here
            updateTime()
            //updateMonitor()

            // Schedule the next update
            handler.postDelayed(this, updateIntervalMillis)
        }
    }

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var updatedBox: TextView
    private lateinit var monitorBox: TextView
    private lateinit var beaconBox: TextView
    private lateinit var icBox: TextView
    private lateinit var adapter: viewPagerAdapter
    fun updateTime() {
        //variable for formatting time
        val time = Calendar.getInstance()
        val y = time.get(Calendar.YEAR)
        val m = time.get(Calendar.MONTH)
        val d = time.get(Calendar.DAY_OF_MONTH)
        val h = time.get(Calendar.HOUR_OF_DAY)
        val mm = time.get(Calendar.MINUTE)

        //format and set text
        val format_time =
            "Last Updated: " + y.toString() + "-" + m.toString() + "-" + d.toString() + " " + h.toString() + ":" + mm.toString()
        updatedBox = findViewById(R.id.lastUpdated)
        updatedBox.setText(format_time)

    }


    fun updateMonitor()
    {
        val status = 0

        if (status == 0)
        {
            monitorBox.setBackgroundColor(getResources().getColor(R.color.warningred))
            monitorBox.setText("Monitor Status: NonFunctional!")

        }

        else
        {
            monitorBox.setBackgroundColor(getResources().getColor(R.color.white))
            monitorBox.setText("Monitor Status: Functional")
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //handler for updating text boxes
        handler.postDelayed(updateRunnable, updateIntervalMillis)

        //find the tablayout and viewpager
        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager2)

        //create an adapter
        adapter = viewPagerAdapter(supportFragmentManager, lifecycle)

        //add tabs
        tabLayout.addTab(tabLayout.newTab().setText("Monitor"))
        tabLayout.addTab(tabLayout.newTab().setText("Fuel Data"))
        tabLayout.addTab(tabLayout.newTab().setText("TEMP"))

        //connect with adapter
        viewPager2.adapter = adapter

        //listen for tab change
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }


        })

        //change tabs
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))

            }

        })
    }
}