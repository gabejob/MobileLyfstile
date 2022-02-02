package com.example.lyfstile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button



class MainActivity : AppCompatActivity(), LayoutFragment.OnDataPass, View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val beginAccCreation = findViewById<View>(R.id.new_user) as Button
        beginAccCreation.setOnClickListener(this);

    }

     override fun onClick(view : View)
    {
        when(view.id) {
            R.id.new_user ->
            {
                val accountCreation = Intent(this, NewUserStepOne::class.java)
                startActivity(accountCreation);

            }
        }
    }


    override fun onDataPass(data: Array<String>) {

    }
}