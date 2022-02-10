package com.example.lyfstile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ReviewInfoScreen : AppCompatActivity(),  View.OnClickListener, PassData{
    private var userInfo = arrayListOf<String>()

    class reviewInfoScreen(newUserInfo: ArrayList<String>){

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun onDataPass(data: Data) {
        TODO("Not yet implemented")
    }
}