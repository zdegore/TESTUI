package com.example.testui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import com.example.testui.R
import java.time.LocalDate

class Monitor1Fragment : Fragment(){
    private lateinit var powerData : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //generate view for frag monitor1
        val view = inflater.inflate(R.layout.fragment_monitor1, container, false)

        // drop down 1
        val spinner: Spinner = view.findViewById(R.id.spinner)

        //set items inside of drop down
        val items = arrayOf(powerData)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

        //connections
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)

        //drop down 2
        val spinner2: Spinner = view.findViewById(R.id.spinner2)

        //set items inside of drop down
        val items2 = arrayOf("Peak Power & Margin", "Core Power: ", "Average Tin:", "Soluble Boron:", "Cycle Burnup:", "Max PIn Burnup:")
        val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items2)

        //connections
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2
        spinner2.setSelection(0)



        return view
    }

    fun updateData(corePowerVal: String, corePowerUnit: String) {
        powerData = corePowerVal + corePowerUnit
    }



}