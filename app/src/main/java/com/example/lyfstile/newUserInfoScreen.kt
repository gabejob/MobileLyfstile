package com.example.lyfstile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val textView1 = ""
private val textView2 = ""
private val textBox1 = ""
private val textBox2 = ""

class newUserInfoScreen : AppCompatActivity(), View.OnClickListener, PassData {

    var dataList = ArrayList<Data>()
    private var currentScreen = 1

    // Hardcode initial values
    private val screenPrompts = arrayListOf<Array<String>>(
        arrayOf("What's your name?", "First Name", "Last Name"),
        arrayOf("Tell us about you", "Age", "Sex"),
        arrayOf("Tell us about you", "Height", "Weight"),
        arrayOf("Where are you from?", "Country", "City"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_user_info)

        //Replace the fragment container(s)
        //Each of these represents a single fragment, so be careful about duplicate tags
        var fnEnterFragment = TextSubmitFragment()
        var lnEnterFragment = TextSubmitFragment()
/*
        var confirmPasswordEnterFragment = TextSubmitFragment()
*/

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.fn_enter_box, fnEnterFragment, "First_Name_box")
        fragtrans.replace(R.id.ln_enter_box, lnEnterFragment, "Last_Name_box")
/*
        fragtrans.replace(R.id.confirm_password_enter_box,confirmPasswordEnterFragment,"Confirm_password_box")
*/

        fragtrans.commit()

        val next_button = findViewById<Button>(R.id.next_button)
        next_button.setOnClickListener(this)
    }

    private fun generateScreenPromptVals() {
        TODO("Not yet implemented")
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

        if(currentScreen + 1 >= screenPrompts.size){
            val cameraScrn = Intent(this, CameraScreen::class.java)
            this.startActivity(cameraScrn)
        }
        else {
            if (dataList != null && allBoxesEntered()) {
                for (entry in dataList) {
                    var temp = entry
                    message += temp.data
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Please enter all forms!", Toast.LENGTH_SHORT).show()
                // This will need to be moved to the place after the data is saved
                currentScreen++
                findViewById<TextView>(R.id.wyn_textView).text = screenPrompts[currentScreen][0]
                findViewById<TextView>(R.id.fn_textView).text = screenPrompts[currentScreen][1]
                findViewById<TextView>(R.id.ln_textView).text = screenPrompts[currentScreen][2]
                // again, this needs to be moved, be called after all information screens have
                // been prompted for

            }
        }
    }

    private fun allBoxesEntered(): Boolean {
        var senders = ArrayList<String>()


        if (dataList != null) {
            for (entry in dataList) {
                var temp = entry
                senders.add(temp.sender)

            }
        }

        if (senders.contains("First_Name_box") && senders.contains("First_Name_box"))
            return true

        return false
    }


    override fun onDataPass(_data: Data) {
        //Toast.makeText(this, "Came from: " + _data.sender + " Data is: " + _data.getAll(), Toast.LENGTH_SHORT).show()
        dataList.add(_data)
        print("in the datapass")

    }

    /*
    * Save data to map for later usage
    */
    private fun saveData(_data: Map<String, String>) {

    }


}