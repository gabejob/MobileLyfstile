package com.example.lyfstile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 * Use the [CameraFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFrag : Fragment(), View.OnClickListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var profileImage: Bitmap? = null

    //    private var dataList = HashMap<String, Data>()
    private var viewModel: LyfViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_camera, container, false)
        viewModel = ViewModelProvider(requireActivity())[LyfViewModel::class.java]

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
                    view.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_camera_to_review)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            // convert photo into bytearray
            val photo = data?.extras?.get("data") as Bitmap
            val bos = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 100, bos)
            val bArray = bos.toByteArray()

            profileImage = data?.extras?.get("data") as Bitmap
            viewModel?.user?.pfp = bArray

            val mIvPic = view?.findViewById<ImageView>(R.id.iv_pic)
            mIvPic?.setImageBitmap(profileImage)
        }
    }

    private fun addNextButton() {
        view?.findViewById<Button>(R.id.Camera)?.text = "Retake Photo"
        view?.findViewById<Button>(R.id.next_button)?.visibility = View.VISIBLE
    }
}