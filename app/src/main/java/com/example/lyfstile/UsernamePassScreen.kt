package com.example.lyfstile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UsernamePassScreen : AppCompatActivity(), View.OnClickListener, PassData{

    var dataList = ArrayList<Data>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username_pass_screen)

        //Replace the fragment container(s)
        //Each of these represents a single fragment, so be careful about duplicate tags
        var emailEnterFragment = TextSubmitFragment()
        var passwordEnterFragment = TextSubmitFragment()
        var confirmPasswordEnterFragment = TextSubmitFragment()

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box,emailEnterFragment,"Email_box")
        fragtrans.replace(R.id.password_enter_box,passwordEnterFragment,"Password_box")
        fragtrans.replace(R.id.confirm_password_enter_box,confirmPasswordEnterFragment,"Confirm_password_box")

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

        var message = ""
        var entries = dataList.toArray()
        if (entries != null) {
            for (entry in entries) {
               var temp = entry as Data
                message += temp.getData(entry.sender)
            }
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    override fun onDataPass(_data: Data) {
        Toast.makeText(this, "Came from: " + _data.sender, Toast.LENGTH_SHORT).show()

        var senderKey = _data.sender
        /*data?.appendData(senderKey,_data.getData(senderKey))*/
        dataList.add(_data)
    }

}