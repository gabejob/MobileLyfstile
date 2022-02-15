package com.example.lyfstile

import android.content.Intent
import java.lang.Exception
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewUserInfoScreen : AppCompatActivity(), View.OnClickListener, PassData {

    private var dataList = HashMap<String, Data>()
    private var currentScreen = "first_last_name"
    private var tag1 = ""
    private var tag2 = ""
    private var user: User? = null
    private var nextButton: Button? = null

    // Hardcode initial values
    private val screenPrompts = mapOf(
        FIRST_LAST_NAME_SCREEN to arrayOf("What's your name?", FIRST_NAME, LAST_NAME),
        AGE_SEX_SCREEN to arrayOf("Tell us about you", AGE, SEX),
        HEIGHT_WEIGHT_SCREEN to arrayOf("Tell us about you", HEIGHT, WEIGHT),
        COUNTRY_CITY_SCREEN to arrayOf("Where are you from?", COUNTRY, CITY)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_user_info)

        val extras = intent.extras
        user = extras?.get("usr_data") as User

        val fnEnterFragment = TextSubmitFragment()
        val lnEnterFragment = TextSubmitFragment()

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.fn_enter_box, fnEnterFragment, FIRST_NAME)
        fragtrans.replace(R.id.ln_enter_box, lnEnterFragment, LAST_NAME)

        fragtrans.commit()

        nextButton = findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (currentScreen) {
            FIRST_LAST_NAME_SCREEN -> {
                changeScreen(currentScreen)
                currentScreen = AGE_SEX_SCREEN
                tag1 = AGE
                tag2 = SEX
            }
            AGE_SEX_SCREEN -> {
                changeScreen(currentScreen)
                currentScreen = HEIGHT_WEIGHT_SCREEN
                tag1 = HEIGHT
                tag2 = WEIGHT
            }
            HEIGHT_WEIGHT_SCREEN -> {
                changeScreen(currentScreen)
                currentScreen = COUNTRY_CITY_SCREEN
                tag1 = COUNTRY
                tag2 = CITY
            }
            COUNTRY_CITY_SCREEN -> {
                addToUserProfile()
                val cameraScrn = Intent(this, CameraScreen::class.java)
                cameraScrn.putExtra("usr_data", user)
                this.startActivity(cameraScrn)
                finish()
            }
        }
        val enterFragment = TextSubmitFragment()
        val enterFragment2 = TextSubmitFragment()

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.fn_enter_box, enterFragment, tag1)
        fragtrans.replace(R.id.ln_enter_box, enterFragment2, tag2)
    }

    override fun onDataPass(data: Data) {
        data as Data
        if (data.data.isEmpty()) {
            dataList.remove(data.sender + currentScreen)
            nextButton?.isEnabled = false
        } else {
            dataList[data.sender + currentScreen] = data

            if (dataList.size == 2) {
                nextButton?.isEnabled = true
            }
        }
    }

    private fun changeScreen(screenName: String) {
        try {
            findViewById<TextView>(R.id.wyn_textView).text = screenPrompts[screenName]?.get(0)
            findViewById<TextView>(R.id.fn_textView).text = screenPrompts[screenName]?.get(1)
            findViewById<TextView>(R.id.ln_textView).text = screenPrompts[screenName]?.get(2)
        } catch (e: Exception) {
            throw Exception("Unable to load next screen")
        }
    }

    private fun addToUserProfile() {
        user?.firstName = dataList[FIRST_NAME]?.data.toString()
        user?.lastName = dataList[LAST_NAME]?.data.toString()
        user?.birthday = dataList[AGE]?.data.toString().toInt()
        user?.sex = dataList[SEX]?.data.toString()
        user?.height = dataList[HEIGHT]?.data.toString()
        user?.weight = dataList[WEIGHT]?.data.toString()
        user?.country = dataList[COUNTRY]?.data.toString()
        user?.city = dataList[CITY]?.data.toString()
    }
}