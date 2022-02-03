package com.example.lyfstile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UsernamePassScreen : AppCompatActivity(), View.OnClickListener, PassData{

    var data : Data ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username_pass_screen)

        //Replace the fragment container

        var fragment1 = TextSubmitFragment()
        val fragtrans = supportFragmentManager.beginTransaction()
       fragtrans.replace(R.id.test,fragment1,"Test")
       fragtrans.commit()

        val next_button = findViewById<Button>(R.id.next_button)
        next_button.setOnClickListener(this)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TextSubmitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TextSubmitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(view: View?) {

    }

    override fun onDataPass(data: Data) {
        Toast.makeText(this, data.sender, Toast.LENGTH_SHORT).show()
    }

}