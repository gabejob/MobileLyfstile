package com.example.lyfstile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity(), View.OnClickListener, PassData {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.Maps_Hiking -> {
                val usernamePassScreen = Intent(this, UsernamePassScreen::class.java)
                this.startActivity(usernamePassScreen)
            }
            R.id.Maps_Parks -> {
                val usernamePassScreen = Intent(this, UsernamePassScreen::class.java)
                this.startActivity(usernamePassScreen)
            }
            R.id.Maps_Gym -> {
                val usernamePassScreen = Intent(this, UsernamePassScreen::class.java)
                this.startActivity(usernamePassScreen)
            }
            R.id.Maps_Restaurants -> {
                val usernamePassScreen = Intent(this, UsernamePassScreen::class.java)
                this.startActivity(usernamePassScreen)
            }
        }
    }

    override fun onDataPass(data: Data) {
    }
}