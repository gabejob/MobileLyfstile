package com.example.lyfstile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class CameraScreen : AppCompatActivity(),  View.OnClickListener, PassData {

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.take_profile_pic)

        val takePictureButton = findViewById<Button>(R.id.Camera)
        takePictureButton.setOnClickListener(this)
    }

     override fun onClick(view : View)
    {
        when(view.id) {
            R.id.Camera ->
            {
                try {
                    startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE)
                } catch (e: Exception){
                    throw Exception("Unable to start the camera")
                }
            }
        }
    }

    override fun onDataPass(data: Data) {
        when(data.sender) {
            "frag" -> {
                //Reward them for submitting their names
                val toast = Toast.makeText(this, data.data, Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val extras = data?.extras as Bundle
            val profileImage = extras.get("data") as Bitmap
            val mIvPic = findViewById<ImageView>(R.id.iv_pic)
            mIvPic.setImageBitmap(profileImage)
        }
    }
}