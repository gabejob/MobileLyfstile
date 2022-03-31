package com.example.lyfstile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewScreenFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewScreenFrag : Fragment(), View.OnClickListener {
    private var createAccountButton: Button? = null
    private var editButton: Button? = null
    private var lyfViewModel : LyfViewModel ?= null
    private var dataList : HashMap<String, Data> ?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_review_screen, container, false)

        createAccountButton = view.findViewById(R.id.create_button)
        createAccountButton?.setOnClickListener(this)

        editButton = view.findViewById(R.id.edit_button)
        editButton?.setOnClickListener(this)
        setInfoValues()
        return view
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.create_button -> {
                try {
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_reviewScreenFrag_to_homeScreenFrag)
                    }
                } catch (e: Exception) {
                    throw Exception("Unable to start the camera")
                }
            }
            R.id.edit_button -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_reviewScreenFrag_to_cameraFrag)
                }

            }
        }
    }
    private fun setInfoValues() {
    //@todo: Pull from viemodel instead
    //        findViewById<ImageView>(R.id.imageView)?.setImageBitmap(dataList?.get(PROFILE_PIC)?.data as Bitmap)
//        findViewById<TextView>(R.id.first_name_box)?.text = dataList?.get(FIRST_NAME)?.data.toString()
//        findViewById<TextView>(R.id.last_name_box)?.text = dataList?.get(LAST_NAME)?.data.toString()
//        findViewById<TextView>(R.id.age_box)?.text = dataList?.get(AGE)?.data.toString()
//        findViewById<TextView>(R.id.sex_box)?.text = dataList?.get(SEX)?.data.toString()
//        findViewById<TextView>(R.id.height_box)?.text = dataList?.get(HEIGHT)?.data.toString()
//        findViewById<TextView>(R.id.weight_box)?.text = dataList?.get(WEIGHT)?.data.toString()
    }
}