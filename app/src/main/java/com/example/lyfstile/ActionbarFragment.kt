package com.example.lyfstile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ActionbarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ActionbarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
/*    var settingsButton: Button = null
    var weatherButton: Button? = null
    var hikerButton: Button? = null
    var healthButton: Button? = null*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_actionbar, container, false)

        val settingsButton = root.findViewById<Button>(R.id.settings) as Button
        settingsButton?.setOnClickListener { this }
        val weatherButton = root.findViewById<Button>(R.id.weather) as Button
        weatherButton?.setOnClickListener{ this }
        val hikerButton = root.findViewById<Button>(R.id.hiker) as Button
        hikerButton?.setOnClickListener{ this }
        val healthButton = root.findViewById<Button>(R.id.health) as Button
        healthButton?.setOnClickListener{this}

        // Inflate the layout for this fragment
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ActionbarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ActionbarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}