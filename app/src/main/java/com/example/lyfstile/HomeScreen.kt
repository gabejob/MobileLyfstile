package com.example.lyfstile

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

class HomeScreen : AppCompatActivity(), ActionbarFragment.ClickInterface {
    private var profilePic: Bitmap? = null
    private var user : User ?= null
    private var lyfViewModel : LyfViewModel ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lyfViewModel = ViewModelProvider( this).get(LyfViewModel::class.java)
        //@todo: replace
        val extras = intent.extras
        user = extras?.get(USER_DATA) as User
        profilePic = intent.getParcelableExtra(PROFILE_PIC)
        setContentView(R.layout.activity_home_screen)

        val bmgFragment = BMIFragment()
        val actionbarFragment = ActionbarFragment()

        val bundle = Bundle()
        bundle.putString(HEIGHT,user?.height)
        bundle.putString(WEIGHT,user?.weight)

        bmgFragment.arguments = bundle
        val fragtrans = supportFragmentManager.beginTransaction()
        fragtrans.replace(R.id.bmi_frag,bmgFragment,BMI)
        fragtrans.replace(R.id.action_bar_fragment,actionbarFragment, ACTION_BAR)
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
                healthScreen.putExtra(USER_DATA, user)
                healthScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(healthScreen)
            }
            R.id.hiker ->
            {
                val mapScreen = Intent(this, MapActivity::class.java)
                mapScreen.putExtra(USER_DATA, user)
                mapScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(mapScreen)
            }
            R.id.weather ->
            {
                val weatherScreen = Intent(this, WeatherActivity::class.java)
                weatherScreen.putExtra(USER_DATA, user)
                weatherScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(weatherScreen)
            }
            R.id.settings ->
            {
                val settingsScreen = Intent(this, SettingsActivity::class.java)
                settingsScreen.putExtra(USER_DATA, user)
                settingsScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(settingsScreen)
            }
        }



    }


}