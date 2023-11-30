package com.example.testui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.testui.R
import com.example.testui.fragments.adapters.viewPagerAdapter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class FuelDataFragment : Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val graph2: GraphView = view.findViewById(R.id.idGraphView1)
        val series = LineGraphSeries(
            arrayOf(
                DataPoint(0.0, 1.0),
                DataPoint(1.0, 5.0),
                DataPoint(2.0, 3.0),
                DataPoint(3.0, 2.0),
                DataPoint(4.0, 6.0)
            )
        )
        graph2.addSeries(series)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fueldata, container, false)
        val spinner: Spinner = view.findViewById(R.id.spinner21)

        val items = arrayOf("CorePower & Burnup", "Core Power: ", "Average Tin:", "Soluble Boron:", "Cycle Burnup:", "Max PIn Burnup:")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)



        return view
    }




}