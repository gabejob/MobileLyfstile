package com.example.lyfstile

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReviewInfoScreen : AppCompatActivity(),  View.OnClickListener, PassData{
    private var userInfo = arrayListOf<String>()
    private var profilePic: Bitmap? = null

    public fun passInfo(newUserInfo: ArrayList<String>){
           // userInfo = intent.getStringArrayList("info_array") as ArrayList<String>
        userInfo = intent.getStringArrayListExtra("myArray")!!
        profilePic = intent.getParcelableExtra("profile_pic")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setInfoValues()
        setContentView(R.layout.review_info)
    }

    private fun setInfoValues() {
        findViewById<ImageView>(R.id.iv_pic)
        for(i in userInfo.size downTo 0){
            val review = "review_actual$i"
            val resID = resources.getIdentifier(review, "id", packageName)
            findViewById<TextView>(resID).text = userInfo[i] ?: "no val found"
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun onDataPass(data: Data) {
        TODO("Not yet implemented")
    }
}