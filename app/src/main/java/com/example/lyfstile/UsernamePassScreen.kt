package com.example.lyfstile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UsernamePassScreen : AppCompatActivity(), View.OnClickListener, PassData {

    private var dataList = HashMap<String, Data>()

    //    private var emailEnterFragment: TextSubmitFragment? = null
    private var nextButton: Button? = null
    private var user: User? = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username_pass_screen)

        //Replace the fragment container(s)
        //Each of these represents a single fragment, so be careful about duplicate tags
        val emailEnterFragment = TextSubmitFragment()
        val passwordEnterFragment = TextSubmitFragment()
        val confirmPasswordEnterFragment = TextSubmitFragment()

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box, emailEnterFragment, "Email_box")
        fragtrans.replace(R.id.password_enter_box, passwordEnterFragment, "Password_box")
        fragtrans.replace(R.id.confirm_password_enter_box, confirmPasswordEnterFragment,"Confirm_password_box")

        fragtrans.commit()

        nextButton = findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)
        nextButton?.isEnabled = false
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
        if (dataList.size == 3) {
            if (dataList["Password_box"]?.data == dataList["Confirm_password_box"]?.data) {
                startNextActivity()
            } else {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter all forms!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startNextActivity() {
        addToUserProfile()
        val fnPassScreen = Intent(this, NewUserInfoScreen::class.java)
        fnPassScreen.putExtra("usr_data", user)
        this.startActivity(fnPassScreen)
    }

    private fun addToUserProfile() {
        user?.email = dataList["Email_box"]?.data.toString()
        user?.password = dataList["Confirm_password_box"]?.data.toString()
    }

    override fun onDataPass(_data: Data) {
        //Toast.makeText(this, "Came from: " + _data.sender, Toast.LENGTH_SHORT).show()
        if (_data.data.isEmpty()) {
            dataList.remove(_data.sender)
            nextButton?.isEnabled = false
        } else {
            dataList[_data.sender] = _data

            if (dataList.size == 3) {
                nextButton?.isEnabled = true
            }
        }
    }

//    private fun allBoxesEntered(): Boolean {
//        val senders = ArrayList<String>()
//
//        if (dataList != null) {
//            for (entry in dataList) {
//                val temp = entry as Data
//                senders.add(temp.sender)
//            }
//        }
//
//        if (senders.contains("Email_box") && senders.contains("Password_box") && senders.contains("Confirm_password_box"))
//            return true
//
//        return false
//    }

    /*
        Warning: dataList is deprecated, should probably use a map
     */
//    private fun doPasswordsMatch(): Boolean {
//        val passField = dataList["Password_box"]?.data
//        val confirmPassField = dataList["Confirm_password_box"]?.data
//
//        if (passField == confirmPassField)
//            return true
//
//        return false
//        return dataList["Password_box"]?.data == dataList["Confirm_password_box"]?.data
//    }



    // not sure if this will be kept here, but ill use it to move to the next frag for now...
/*        val fnPassScreen = Intent(this, FnPassScreen::class.java)
        this.startActivity(fnPassScreen)*/
}

