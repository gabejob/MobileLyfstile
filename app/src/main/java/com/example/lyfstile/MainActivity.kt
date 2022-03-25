package com.example.lyfstile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), View.OnClickListener, PassData {
    private var lyfViewModel : LyfViewModel ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get the viewmodel
        //lyfViewModel = ViewModelProvider( this).get(LyfViewModel::class.java)


        val createAccountButton = findViewById<Button>(R.id.new_user)
        createAccountButton.setOnClickListener(this)
        val existingAccountButton = findViewById<Button>(R.id.Log_in)
        existingAccountButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.new_user -> {
                val usernamePassScreen = Intent(this, UsernamePassScreen::class.java)
                this.startActivity(usernamePassScreen)
            }
            R.id.Log_in -> {
                val existingUserScreen = Intent(this, LoginExistingAccount::class.java)
                this.startActivity(existingUserScreen)
            }
        }
    }

    override fun onDataPass(data: Data) {
    }
}