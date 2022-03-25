package com.example.lyfstile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception

class ReviewInfoScreen : AppCompatActivity(), View.OnClickListener, PassData {
    private var createAccountButton: Button? = null
    private var editButton: Button? = null
    private var lyfViewModel : LyfViewModel ?= null
    private var dataList : HashMap<String, Data> ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lyfViewModel = ViewModelProvider( this).get(LyfViewModel::class.java)
        //dataList = lyfViewModel?.getCurrentUser()
        setContentView(R.layout.review_info)

        createAccountButton = findViewById(R.id.create_button)
        createAccountButton?.setOnClickListener(this)

        editButton = findViewById(R.id.edit_button)
        editButton?.setOnClickListener(this)
        setInfoValues()
    }

    //TODO: Fix this to be automated
    private fun setInfoValues() {
        findViewById<ImageView>(R.id.imageView)?.setImageBitmap(dataList?.get(PROFILE_PIC)?.data as Bitmap)
        findViewById<TextView>(R.id.first_name_box)?.text = dataList?.get(FIRST_NAME)?.data.toString()
        findViewById<TextView>(R.id.last_name_box)?.text = dataList?.get(LAST_NAME)?.data.toString()
        findViewById<TextView>(R.id.age_box)?.text = dataList?.get(AGE)?.data.toString()
        findViewById<TextView>(R.id.sex_box)?.text = dataList?.get(SEX)?.data.toString()
        findViewById<TextView>(R.id.height_box)?.text = dataList?.get(HEIGHT)?.data.toString()
        findViewById<TextView>(R.id.weight_box)?.text = dataList?.get(WEIGHT)?.data.toString()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.create_button -> {
                try {
                    val homeScreen = Intent(this, HomeScreen::class.java)
                    this.startActivity(homeScreen)
                } catch (e: Exception) {
                    throw Exception("Unable to start the camera")
                }
            }
            R.id.edit_button -> {
                val newUserInfoScreen = Intent(this, NewUserInfoScreen::class.java)

                finish()
                this.startActivity(newUserInfoScreen)
            }
        }
    }

    override fun onDataPass(data: Data) {
        TODO("Not yet implemented")
    }
}