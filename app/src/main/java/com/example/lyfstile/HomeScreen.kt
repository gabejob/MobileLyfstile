package com.example.lyfstile

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HomeScreen : AppCompatActivity() {


    private var profilePic: Bitmap? = null
    private var user : User ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        user = extras?.get("usr_data") as User
        profilePic = intent.getParcelableExtra("profile_pic")
        setContentView(R.layout.activity_home_screen)


        var bmgFragment = BMIFragment()
        var actionbarFragment = ActionbarFragment()

        val fragtrans = supportFragmentManager.beginTransaction()
        fragtrans.replace(R.id.bmi_frag,bmgFragment,"bmi_box")
        fragtrans.replace(R.id.action_bar_fragment,actionbarFragment,"action_bar")
        fragtrans.commit()
        findViewById<ImageView>(R.id.pfp_box)?.setImageBitmap(profilePic)


    }




}