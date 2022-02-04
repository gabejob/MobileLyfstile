package com.example.lyfstile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(),  View.OnClickListener, PassData {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var createAccountButton = findViewById<Button>(R.id.new_user)
        createAccountButton.setOnClickListener(this)

    }

     override fun onClick(view : View)
    {
        when(view.id) {
            R.id.new_user ->
            {


                val usernamePassScreen = Intent(this, UsernamePassScreen::class.java)
                this.startActivity(usernamePassScreen)

            }
        }
    }

    override fun onDataPass(data: Data) {
        when(data?.sender) {
            "frag" -> {
                //Reward them for submitting their names
                val toast = Toast.makeText(this, data.data["frag"], Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }


}