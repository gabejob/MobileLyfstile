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

class NewUserInfoScreen : AppCompatActivity(), View.OnClickListener, PassData {

    private var dataList = HashMap<String, Data>()
    private var currentScreen = 1
    private var user: User? = null

    var nextButton: Button? = null

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

        //Get Bundle intent
        //****NOTE**** DO NOT CHANGE KEY FROM "usr_data" WHEN PASSING INTENTS!! ****NOTE****
        var extras = intent.extras
        user = extras?.get("usr_data") as User


        //Replace the fragment container(s)
        //Each of these represents a single fragment, so be careful about duplicate tags
        var fnEnterFragment = TextSubmitFragment()
        var lnEnterFragment = TextSubmitFragment()

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.fn_enter_box, fnEnterFragment, "First_box")
        fragtrans.replace(R.id.ln_enter_box, lnEnterFragment, "Second_box")

        fragtrans.commit()

        nextButton = findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)
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

        if (currentScreen == 4) {

            addToUserProfile()

            val cameraScrn = Intent(this, CameraScreen::class.java)
            cameraScrn.putExtra("usr_data", user)
            this.startActivity(cameraScrn)
            finish()

        } else {

            // This will need to be moved to the place after the data is saved
            //saveData(3)

            findViewById<TextView>(R.id.wyn_textView).text = screenPrompts[currentScreen][0]
            findViewById<TextView>(R.id.fn_textView).text = screenPrompts[currentScreen][1]
            findViewById<TextView>(R.id.ln_textView).text = screenPrompts[currentScreen][2]
            currentScreen++

            // Erase values from text edits
            var EnterFragment = TextSubmitFragment()
            var EnterFragment2 = TextSubmitFragment()

            val fragtrans = supportFragmentManager.beginTransaction()

            fragtrans.replace(R.id.fn_enter_box, EnterFragment, "First_box")
            fragtrans.replace(R.id.ln_enter_box, EnterFragment2, "Second_box")

            fragtrans.commit()

            // again, this needs to be moved, be called after all information screens have
            // been prompted for


        }
    }

    private fun allBoxesEntered(): Boolean {
        var senders = ArrayList<String>()


        if (dataList != null) {
            for (entry in dataList) {
                var temp = entry as Data
                senders.add(temp.sender)

            }
        }

        if (dataList.size == 9)
            return true

        return false
    }

    override fun onDataPass(_data: Data) {
        saveData(_data)
    }

    private fun addToUserProfile() {
        //First and last name
        user?.firstName = dataList["First_box1"]?.data.toString()
        user?.lastName = dataList["Second_box1"]?.data.toString()

        //Age and Sex
        //user?.birthday = dataList["First_box2"].toString()
        // just age and not DOB for now
        user?.birthday = dataList["First_box2"]?.data.toString().toInt()
        user?.sex = dataList["Second_box2"]?.data.toString()

        //Height and Weight
        user?.height = dataList["First_box3"]?.data.toString()
        user?.weight = dataList["Second_box3"]?.data.toString()

        //Country and City
        user?.country = dataList["First_box4"]?.data.toString()
        user?.city = dataList["Second_box4"]?.data.toString()
    }

    /*
    * Save data for later usage
    */
    private fun saveData(_data: Any) {
        _data as Data
        if (_data.data.isEmpty()) {
            dataList.remove(_data.sender + currentScreen)
            nextButton?.isEnabled = false
        } else {
            dataList[_data.sender + currentScreen] = _data

            if (dataList.size == 2) {
                nextButton?.isEnabled = true
            }
        }

    }


}