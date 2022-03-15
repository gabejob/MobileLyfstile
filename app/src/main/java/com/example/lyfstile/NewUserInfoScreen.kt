package com.example.lyfstile

import android.content.Intent
import java.lang.Exception
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


//@todo *******We really need to re-evaluate how this works... back button takes you to user/pass screen*********
class NewUserInfoScreen : AppCompatActivity(), View.OnClickListener, PassData {

    private var dataList = HashMap<String, Data>()
    private var currentScreen = "first_last_name"
    private var tag1 = ""
    private var tag2 = ""
    private var user: User? = null
    private var nextButton: Button? = null
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
        user = extras?.get(USER_DATA) as User
        buildDataList(user!!)

        fragTrans(FIRST_NAME, LAST_NAME)

        nextButton = findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)

        nextButton?.isEnabled = false
    }


    private fun buildDataList(user: User){
        if(user.firstName.isNullOrEmpty()) {
            nextButton?.isEnabled = false
        }else{

            dataList[FIRST_NAME] = (Data(FIRST_NAME,user.firstName))
            dataList[LAST_NAME] = (Data(LAST_NAME,user.lastName))
            dataList[COUNTRY] = (Data(COUNTRY,user.country))
            dataList[CITY] = (Data(CITY,user.city))
            dataList[AGE] = (Data(AGE,user.birthday))
            dataList[SEX] = (Data(SEX,user.sex))
            dataList[WEIGHT] = (Data(WEIGHT,user.weight))
            dataList[HEIGHT] = (Data(HEIGHT,user.height))

        }

    }

    override fun onBackPressed() {
        if(currentScreen == "first_last_name") {
            super.onBackPressed()
            finish()
        }else if(currentScreen == AGE_SEX_SCREEN){
            currentScreen = FIRST_LAST_NAME_SCREEN
            changeScreen(currentScreen)
            tag1 = FIRST_NAME
            tag2 = LAST_NAME
        }
        else if(currentScreen == HEIGHT_WEIGHT_SCREEN){
            currentScreen = AGE_SEX_SCREEN
            changeScreen(currentScreen)
            tag1 = AGE
            tag2 = SEX
        }
        else if(currentScreen == COUNTRY_CITY_SCREEN){
            currentScreen = HEIGHT_WEIGHT_SCREEN
            changeScreen(currentScreen)
            tag1 = HEIGHT
            tag2 = WEIGHT
        }
        fragTrans(tag1, tag2)
    }
    override fun onClick(view: View?) {

        if(dataList[FIRST_NAME]?.equals("") == true)
            nextButton?.isEnabled = false

        when(view?.id) {
            //Each time the next button is pressed, change tags and replace text fragments...
                R.id.next_button -> {
                    currentFocus?.clearFocus()
                    when (currentScreen) {
                        FIRST_LAST_NAME_SCREEN -> {
                            currentScreen = AGE_SEX_SCREEN
                            changeScreen(currentScreen)
                            tag1 = AGE //Needs to enforce numbers/date selector
                            tag2 = SEX //Needs to enforce dropdown
                        }
                        AGE_SEX_SCREEN -> {
                            currentScreen = HEIGHT_WEIGHT_SCREEN
                            changeScreen(currentScreen)
                            tag1 = HEIGHT //Needs to enforce dropdowns
                            tag2 = WEIGHT //Needs to enforce dropdowns
                        }
                        HEIGHT_WEIGHT_SCREEN -> {
                            currentScreen = COUNTRY_CITY_SCREEN
                            changeScreen(currentScreen)
                            tag1 = COUNTRY //Needs to enforce country auto-select
                            tag2 = CITY //Needs to enforce city auto-select
                        }
                        COUNTRY_CITY_SCREEN -> {
                            addToUserProfile()
                            val cameraScrn = Intent(this, CameraScreen::class.java)
                            cameraScrn.putExtra(USER_DATA, user)
                            this.startActivity(cameraScrn)
                            finish()
                        }
                    }
                    fragTrans(tag1, tag2)

                }
        }
    }

    /**
     *
     *
     * Function to handle all swaps between prompts/fragments
     *
     *
    */
    private fun changeScreen(screenName: String) {
        try {
            findViewById<TextView>(R.id.wyn_textView).text = screenPrompts[screenName]?.get(0)
            findViewById<TextView>(R.id.fn_textView).text = screenPrompts[screenName]?.get(1)
            findViewById<TextView>(R.id.ln_textView).text = screenPrompts[screenName]?.get(2)


        } catch (e: Exception) {
            throw Exception("Unable to load next screen")
        }
    }

    /**
    *
    * Fragment initialization...
    *
    */
    private fun fragTrans(tag1 : String, tag2 : String) {


        val fragtrans = supportFragmentManager.beginTransaction()

        var enterFragment = TextSubmitFragment()
        val enterFragment2 = TextSubmitFragment()

        if (!dataList[tag1]?.data.isNullOrEmpty()) {
            enterFragment.value = dataList[tag1]?.data.toString()
        }
        if(!dataList[tag2]?.data.isNullOrEmpty()){
            enterFragment2.value = dataList[tag2]?.data.toString()
        }

        fragtrans.replace(R.id.fn_enter_box, enterFragment, tag1)
        fragtrans.replace(R.id.ln_enter_box, enterFragment2, tag2)
        currentFocus?.clearFocus()
        fragtrans.commit()

/*        if (checkVal(tag1) != "Not provided") {
            enterFragment.enterTxt?.setText(dataList[tag1]?.data)
        }
        if(checkVal(tag2) != "Not provided"){
            enterFragment2.enterTxt?.setText(dataList[tag2]?.data)
        }*/
    }

    /**
     *
     * All user data needs to be manually inserted into a user object
     *
     *
     */
    private fun addToUserProfile() {
        user?.firstName = checkVal(FIRST_NAME)
        user?.lastName = checkVal(LAST_NAME)
        user?.birthday = checkVal(AGE)
        user?.sex = checkVal(SEX)
        user?.height = checkVal(HEIGHT)
        user?.weight = checkVal(WEIGHT)
        user?.country = checkVal(COUNTRY)
        user?.city = checkVal(CITY)
    }

    private fun checkVal(tag : String) : String
    {
        if(dataList[tag]?.data.isNullOrEmpty())
            return "Not Provided"
        return dataList[tag]?.data.toString()
    }
    override fun onDataPass(data: Data) {
        if (data.data.isEmpty()) {
            dataList.remove(data.sender)
            nextButton?.isEnabled = false
        } else {
            dataList[data.sender] = data
            if (dataList.size % 2 == 0) {
                nextButton?.isEnabled = true
            }
        }
    }

}