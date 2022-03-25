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

class SettingsActivity : AppCompatActivity(), View.OnClickListener, PassData {
    private var profilePic: Bitmap? = null
    private var user: User? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null
    private var lyfViewModel : LyfViewModel ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lyfViewModel = ViewModelProvider( this).get(LyfViewModel::class.java)

        val extras = intent.extras
        user = extras?.get(USER_DATA) as User
        profilePic = intent.getParcelableExtra(PROFILE_PIC)
        setContentView(R.layout.settings)

        saveButton = findViewById(R.id.settings_save_button)
        saveButton?.setOnClickListener(this)

        cancelButton = findViewById(R.id.settings_cancel_button)
        cancelButton?.setOnClickListener(this)
        setInfoValues()
    }

    //TODO: Fix this to be automated
    private fun setInfoValues() {
        findViewById<ImageView>(R.id.settings_imageView)?.setImageBitmap(profilePic)
        findViewById<TextView>(R.id.settings_fn_edit_box)?.text = user?.firstName
        findViewById<TextView>(R.id.settings_ln_edit_box)?.text = user?.lastName
        findViewById<TextView>(R.id.settings_age_edit_box)?.text = user?.birthday.toString()
        findViewById<TextView>(R.id.settings_sex_edix_box)?.text = user?.sex
        findViewById<TextView>(R.id.settings_height_edit_box)?.text = user?.height
        findViewById<TextView>(R.id.settings_weight_edit_box)?.text = user?.weight
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.settings_save_button -> {
                val homeScreen = Intent(this, HomeScreen::class.java)
                user?.firstName = findViewById<TextView>(R.id.settings_fn_edit_box)?.text.toString()
                user?.lastName = findViewById<TextView>(R.id.settings_ln_edit_box)?.text.toString()
                user?.birthday = findViewById<TextView>(R.id.settings_age_edit_box)?.text.toString()
                user?.sex = findViewById<TextView>(R.id.settings_sex_edix_box)?.text.toString()
                user?.height = findViewById<TextView>(R.id.settings_height_edit_box)?.text.toString()
                user?.weight = findViewById<TextView>(R.id.settings_weight_edit_box)?.text.toString()
                homeScreen.putExtra(USER_DATA, user)
                homeScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(homeScreen)
            }
            R.id.settings_cancel_button -> {
                val homeScreen = Intent(this, HomeScreen::class.java)
                homeScreen.putExtra(USER_DATA, user)
                homeScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(homeScreen)
            }
        }
    }

    override fun onDataPass(data: Data) {
        when(data.sender){

        }
    }
}