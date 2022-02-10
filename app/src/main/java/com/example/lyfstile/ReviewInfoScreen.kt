package com.example.lyfstile

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReviewInfoScreen : AppCompatActivity(),  View.OnClickListener, PassData{
    private var dataList = HashMap<String, Data>()
    private var profilePic: Bitmap? = null

    public fun passInfo(newUserInfo: ArrayList<String>){
           // userInfo = intent.getStringArrayList("info_array") as ArrayList<String>
        var extras = intent.extras
        dataList = extras?.get("usr_data") as HashMap<String, Data>
        profilePic = intent.getParcelableExtra("profile_pic")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setInfoValues()
        setContentView(R.layout.review_info)
    }

    private fun setInfoValues() {
        findViewById<ImageView>(R.id.iv_pic)

        //NOTE 2/9/2022 10:51 PM I am tired, I am going to bed, this just needs to be swapped for hashmap
        //stuff :)
       // for(i in dataList.size downTo 0){
       //     val review = "review_actual$i"
       //     val resID = resources.getIdentifier(review, "id", packageName)
       //    findViewById<TextView>(resID).text = userInfo[i] ?: "no val found"
       // }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun onDataPass(data: Data) {
        TODO("Not yet implemented")
    }
}