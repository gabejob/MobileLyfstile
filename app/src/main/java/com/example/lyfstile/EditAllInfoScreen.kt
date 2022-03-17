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

class EditAllInfoScreen : AppCompatActivity(), View.OnClickListener, PassData {
    private var profilePic: Bitmap? = null
    private var user: User? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        user = extras?.get(USER_DATA) as User
        profilePic = intent.getParcelableExtra(PROFILE_PIC)
        setContentView(R.layout.edit_review_info)

        saveButton = findViewById(R.id.save_button)
        saveButton?.setOnClickListener(this)

        cancelButton = findViewById(R.id.cancel_button)
        cancelButton?.setOnClickListener(this)
        setInfoValues()
    }

    //TODO: Fix this to be automated
    private fun setInfoValues() {
        findViewById<ImageView>(R.id.imageView)?.setImageBitmap(profilePic)
        findViewById<TextView>(R.id.fn_edit_box)?.text = user?.firstName
        findViewById<TextView>(R.id.ln_edit_box)?.text = user?.lastName
        findViewById<TextView>(R.id.age_edit_box)?.text = user?.birthday.toString()
        findViewById<TextView>(R.id.sex_edix_box)?.text = user?.sex
        findViewById<TextView>(R.id.height_edit_box)?.text = user?.height
        findViewById<TextView>(R.id.weight_edit_box)?.text = user?.weight
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.save_button -> {
                try {
                    val reviewScreen = Intent(this, ReviewInfoScreen::class.java)
                    reviewScreen.putExtra(USER_DATA, user)
                    reviewScreen.putExtra(PROFILE_PIC, profilePic)
                    this.startActivity(reviewScreen)
                } catch (e: Exception) {
                    throw Exception("Error saving info")
                }
            }
            R.id.cancel_button -> {
                val originalReviewScreen = Intent(this, ReviewInfoScreen::class.java)
                originalReviewScreen.putExtra(USER_DATA, user)
                originalReviewScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(originalReviewScreen)
                finish()
            }
        }
    }

    override fun onDataPass(data: Data) {
        when(data.sender){

        }
    }
}