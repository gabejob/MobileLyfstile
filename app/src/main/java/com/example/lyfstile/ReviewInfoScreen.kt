package com.example.lyfstile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class ReviewInfoScreen : AppCompatActivity(), View.OnClickListener, PassData {
    private var profilePic: Bitmap? = null
    private var user: User? = null
    private var createAccountButton: Button? = null
    private var editButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        user = extras?.get(USER_DATA) as User
        profilePic = intent.getParcelableExtra(PROFILE_PIC)
        setContentView(R.layout.review_info)

        createAccountButton = findViewById(R.id.create_button)
        createAccountButton?.setOnClickListener(this)

        editButton = findViewById(R.id.edit_button)
        editButton?.setOnClickListener(this)
        setInfoValues()
    }

    //TODO: Fix this to be automated
    private fun setInfoValues() {
        findViewById<ImageView>(R.id.imageView)?.setImageBitmap(profilePic)
        findViewById<TextView>(R.id.first_name_box)?.text = user?.firstName
        findViewById<TextView>(R.id.last_name_box)?.text = user?.lastName
        findViewById<TextView>(R.id.age_box)?.text = user?.birthday.toString()
        findViewById<TextView>(R.id.sex_box)?.text = user?.sex
        findViewById<TextView>(R.id.height_box)?.text = user?.height
        findViewById<TextView>(R.id.weight_box)?.text = user?.weight
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.create_button -> {
                try {
                    val homeScreen = Intent(this, HomeScreen::class.java)
                    homeScreen.putExtra(USER_DATA, user)
                    homeScreen.putExtra(PROFILE_PIC, profilePic)
                    this.startActivity(homeScreen)
                } catch (e: Exception) {
                    throw Exception("Unable to start the camera")
                }
            }
            R.id.edit_button -> {
                val newUserInfoScreen = Intent(this, NewUserInfoScreen::class.java)
                newUserInfoScreen.putExtra(USER_DATA, user)
                newUserInfoScreen.putExtra(PROFILE_PIC, profilePic)
                finish()
                this.startActivity(newUserInfoScreen)
            }
        }
    }

    override fun onDataPass(data: Data) {
        TODO("Not yet implemented")
    }
}