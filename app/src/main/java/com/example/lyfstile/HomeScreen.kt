package com.example.lyfstile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeScreen : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        var bmgFragment = BMIFragment()

        val fragtrans = supportFragmentManager.beginTransaction()
        fragtrans.replace(R.id.bmi_frag,bmgFragment,"bmi_box")
        fragtrans.commit()


    }




}