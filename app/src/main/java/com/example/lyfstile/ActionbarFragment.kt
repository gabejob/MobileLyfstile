package com.example.lyfstile

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ActionbarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ActionbarFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun actionButtonClicked(id: Int);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_actionbar, container, false)

        val settingsButton = root.findViewById<Button>(R.id.settings)
        settingsButton?.setOnClickListener(this)
        val weatherButton = root.findViewById<Button>(R.id.weather)
        weatherButton?.setOnClickListener(this)
        val hikerButton = root.findViewById<Button>(R.id.hiker)
        hikerButton?.setOnClickListener(this)
        val healthButton = root.findViewById<Button>(R.id.health)
        healthButton?.setOnClickListener(this)
        val homeButton = root.findViewById<Button>(R.id.home)
        homeButton?.setOnClickListener(this)

        // Inflate the layout for this fragment
        return root
    }

    fun bindClickInterface(inter: ClickInterface) {
        clickInterface = inter
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.health -> {
                clickInterface?.actionButtonClicked(R.id.health)
            }
            R.id.hiker -> {
                clickInterface?.actionButtonClicked(R.id.hiker)
            }
            R.id.weather -> {
                clickInterface?.actionButtonClicked(R.id.weather)
            }
            R.id.home -> {
                clickInterface?.actionButtonClicked(R.id.home)
            }
            R.id.settings -> {
                clickInterface?.actionButtonClicked(R.id.settings)
            }
        }
    }
}