package com.example.lyfstile

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class ReviewInfoScreen : AppCompatActivity(), View.OnClickListener, PassData {
    private var dataList = HashMap<String, Data>()
    private var profilePic: Bitmap? = null
    private var user: User? = null
    private var view: View? = null
    private var createAccountButton: Button? = null
    private var editButton: Button? = null
    public fun passInfo(newUserInfo: ArrayList<String>) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        user = extras?.get("usr_data") as User
        profilePic = intent.getParcelableExtra("profile_pic")
        setContentView(R.layout.review_info)

        createAccountButton = findViewById(R.id.create_button)
        createAccountButton?.setOnClickListener(this)

        editButton = findViewById(R.id.edit_button)
        editButton?.setOnClickListener(this)
        setInfoValues()

    }

    //TODO: Fix this to be automated
    private fun setInfoValues() {
        findViewById<ImageView>(R.id.iv_pic)
        var userVals = arrayOf(
            "first_name_box",
            "last_name_box",
            "age_box",
            "sex_box",
            "height_box",
            "weight_box"
        )

        //THIS IS UGLY! CHANGE THIS! JUST GETTING THIS WORKING FOR CHECKPOINT
        var str = findViewById<TextView>(R.id.first_name_box)?.text
        findViewById<TextView>(R.id.first_name_box)?.text = user?.firstName
        findViewById<TextView>(R.id.last_name_box)?.text = user?.lastName


        //Age does not work if we ask the user to enter DOB until Date format is implemented
        findViewById<TextView>(R.id.age_box)?.text = user?.birthday.toString()

        findViewById<TextView>(R.id.sex_box)?.text = user?.sex
        findViewById<TextView>(R.id.height_box)?.text = user?.height
        findViewById<TextView>(R.id.weight_box)?.text = user?.weight

        findViewById<ImageView>(R.id.imageView)?.setImageBitmap(profilePic)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.create_button -> {
                try {
                    val homeScreen = Intent(this, HomeScreen::class.java)
                    homeScreen.putExtra("usr_data", user)
                    homeScreen.putExtra("profile_pic", profilePic)
                    this.startActivity(homeScreen)
                } catch (e: Exception) {
                    throw Exception("Unable to start the camera")
                }
            }
            R.id.edit_button -> {
                val usernamePassScreen = Intent(this, UsernamePassScreen::class.java)
                usernamePassScreen.putExtra("usr_data", user)
                usernamePassScreen.putExtra("profile_pic", profilePic)
                this.startActivity(usernamePassScreen)
                finish()
            }
        }
    }

    override fun onDataPass(data: Data) {
        TODO("Not yet implemented")
    }
}