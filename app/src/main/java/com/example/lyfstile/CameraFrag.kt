package com.example.lyfstile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CameraFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFrag : Fragment(), View.OnClickListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var profileImage: Bitmap? = null
    private var dataList = HashMap<String, Data>()
    private var lyfViewModel : LyfViewModel ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_camera, container, false)
        lyfViewModel = ViewModelProvider( this).get(LyfViewModel::class.java)

        val takePictureButton = view.findViewById<Button>(R.id.Camera)
        takePictureButton.setOnClickListener(this)

        val next_button = view.findViewById<Button>(R.id.next_button)
        next_button.setOnClickListener(this)
        return view
    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.Camera -> {
                try {
                    startActivityForResult(
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        REQUEST_IMAGE_CAPTURE
                    )
                    addNextButton();
                } catch (e: Exception) {
                    throw Exception("Unable to start the camera")
                }
            }
            R.id.next_button -> {
                if (profileImage == null) {
                    val toast = Toast.makeText(
                        requireActivity(),
                        "No photo was taken, please take a photo",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_cameraFrag_to_reviewScreenFrag)
                    }
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val extras = data?.extras as Bundle
            profileImage = extras.get("data") as Bitmap
            val mIvPic = view?.findViewById<ImageView>(R.id.iv_pic)
            mIvPic?.setImageBitmap(profileImage)
        }
    }
    private fun addNextButton() {
        view?.findViewById<Button>(R.id.Camera)?.text = "Retake Photo"
        view?.findViewById<Button>(R.id.next_button)?.visibility = View.VISIBLE
    }
}