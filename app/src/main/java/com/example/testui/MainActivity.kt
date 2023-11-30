package com.example.testui

import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.widget.ArrayAdapter

import android.widget.AutoCompleteTextView
import android.widget.Spinner
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
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
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

            // Core Power
            val corePowerVal = parseXML("CMSUM2", "value") // 100
            val corePowerUnit = parseXML("CMSUM2", "unit") // %

            //

            // Get reference to your fragment
            val fragment = supportFragmentManager.findFragmentById(R.id.monitor1) as Monitor1Fragment

            // Update data in your fragment
            fragment.updateData(corePowerVal, corePowerUnit)

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

    private fun parseXML(item: String, attribute: String) : String {
        val parserFactory: XmlPullParserFactory
        try {
            parserFactory = XmlPullParserFactory.newInstance()
            val parser = parserFactory.newPullParser()
            val `is` = assets.open("monitor.xml")
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(`is`, null)
            return processParsing(parser, item, attribute)
        } catch (e: XmlPullParserException) {
        } catch (e: IOException) {
        }
        return ""
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun processParsing(parser: XmlPullParser, item: String, value: String) : String{
        var eventType = parser.eventType
        var currentItem: Data? = null
        var isItem = false
        while (eventType != XmlPullParser.END_DOCUMENT) {
            var temp: String? = null
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    temp = parser.name
                    if (item == temp) {
                        currentItem = Data()
                        isItem = true
                    } else if (currentItem != null && isItem) {
                        if ("value" == temp) {
                            currentItem.value = parser.nextText()
                        } else if ("description" == temp) {
                            currentItem.description = parser.nextText()
                        } else if ("quality" == temp) {
                            currentItem.quality = parser.nextText()
                        } else if ("unit" == temp) {
                            currentItem.unit = parser.nextText()
                        } else if ("type" == temp) {
                            currentItem.type = parser.nextText()
                            isItem = false
                        }
                    }
                }
            }
            eventType = parser.next()
        }
        return printData(currentItem, value)
    }

    private fun printData(currentItem: Data?, value: String) : String{
        val builder = StringBuilder()
        when (value) {
            "value" -> if (currentItem != null) {
                builder.append(currentItem.value)
            }
            "description" -> if (currentItem != null) {
                builder.append(currentItem.description)
            }
            "quality" -> if (currentItem != null) {
                builder.append(currentItem.quality)
            }
            "unit" -> if (currentItem != null) {
                builder.append(currentItem.unit)
            }
            "type" -> if (currentItem != null) {
                builder.append(currentItem.type)
            }
        }
        return builder.toString()
    }
}



