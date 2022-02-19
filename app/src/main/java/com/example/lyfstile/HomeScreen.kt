package com.example.lyfstile

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HomeScreen : AppCompatActivity(), ActionbarFragment.ClickInterface {


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

        var bundle = Bundle()
        bundle.putString("height",user?.height)
        bundle.putString("weight",user?.weight)

        bmgFragment.arguments = bundle
        val fragtrans = supportFragmentManager.beginTransaction()
        fragtrans.replace(R.id.bmi_frag,bmgFragment,"bmi_box")
        fragtrans.replace(R.id.action_bar_fragment,actionbarFragment,"action_bar")
        fragtrans.commit()
        findViewById<ImageView>(R.id.pfp_box)?.setImageBitmap(profilePic)
        actionbarFragment.bindClickInterface(this)

    }

    override fun actionButtonClicked(id: Int) {

        when(id)
        {
            R.id.health ->
            {
                val healthScreen = Intent(this, HealthActivity::class.java)
                healthScreen.putExtra("usr_data", user)
                healthScreen.putExtra("profile_pic", profilePic)

                this.startActivity(healthScreen)
            }
        }



    }


}