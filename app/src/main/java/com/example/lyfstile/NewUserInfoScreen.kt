package com.example.lyfstile

import android.content.Intent
import java.lang.Exception
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KProperty

//@todo *******We really need to re-evaluate how this works... back button takes you to user/pass screen*********
class NewUserInfoScreen : AppCompatActivity(), View.OnClickListener, PassData {

    private var dataList = HashMap<String, Data>()
    private var currentScreen = "first_last_name"
    private var tag1 = ""
    private var tag2 = ""

    private var nextButton: Button? = null
    private var viewModel : LyfViewModel by viewModels()
    private val screenPrompts = mapOf(
        FIRST_LAST_NAME_SCREEN to arrayOf("What's your name?", FIRST_NAME, LAST_NAME),
        AGE_SEX_SCREEN to arrayOf("Tell us about you", AGE, SEX),
        HEIGHT_WEIGHT_SCREEN to arrayOf("Tell us about you", HEIGHT, WEIGHT),
        COUNTRY_CITY_SCREEN to arrayOf("Where are you from?", COUNTRY, CITY)
    )
    var user:User? = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_user_info)
        viewModel = ViewModelProvider(this)[LyfViewModel::class.java]

        viewModel.allUsers(this)!!.observe(this) {
            if (it == null) {

            } else {
                println(it)
            }
        }


        // dataList = viewModel.data

        fragTrans(FIRST_NAME, LAST_NAME)
        nextButton = findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)

        nextButton?.isEnabled = false
    }
    private fun buildDataList(user: User){
        if(user.firstName.isNullOrEmpty()) {
            nextButton?.isEnabled = false
        }else{

            dataList?.set(FIRST_NAME, (Data(FIRST_NAME,user.firstName)))
            dataList?.set(LAST_NAME, (Data(LAST_NAME,user.lastName)))
            dataList?.set(COUNTRY, (Data(COUNTRY,user.country)))
            dataList?.set(CITY, (Data(CITY,user.city)))
            dataList?.set(AGE, (Data(AGE,user.birthday)))
            dataList?.set(SEX, (Data(SEX,user.sex)))
            dataList?.set(WEIGHT, (Data(WEIGHT,user.weight)))
            dataList?.set(HEIGHT, (Data(HEIGHT,user.height)))

        }

    }

    override fun onBackPressed() {
        when (currentScreen) {
            "first_last_name" -> {
                super.onBackPressed()
                finish()
            }
            AGE_SEX_SCREEN -> {
                currentScreen = FIRST_LAST_NAME_SCREEN
                changeScreen(currentScreen)
                tag1 = FIRST_NAME
                tag2 = LAST_NAME
            }
            HEIGHT_WEIGHT_SCREEN -> {
                currentScreen = AGE_SEX_SCREEN
                changeScreen(currentScreen)
                tag1 = AGE
                tag2 = SEX
            }
            COUNTRY_CITY_SCREEN -> {
                currentScreen = HEIGHT_WEIGHT_SCREEN
                changeScreen(currentScreen)
                tag1 = HEIGHT
                tag2 = WEIGHT
            }
        }
        fragTrans(tag1, tag2)
    }
    override fun onClick(view: View?) {

        if(dataList?.get(FIRST_NAME)?.equals("") == true)
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

        var enterFragment = TextSubmitFragment();
        val enterFragment2 = TextSubmitFragment()

        if (!dataList?.get(tag1)?.data.toString().isNullOrEmpty()) {
            enterFragment.value = dataList?.get(tag1)?.data.toString()
        }
        if(!dataList?.get(tag2)?.data.toString().isNullOrEmpty()){
            enterFragment2.value = dataList?.get(tag2)?.data.toString()
        }

        fragtrans.replace(R.id.fn_enter_box, enterFragment, tag1)
        fragtrans.replace(R.id.ln_enter_box, enterFragment2, tag2)

        enterFragment.requestFocus()
        fragtrans.commit()

    }

    /**
     *
     * All user data needs to be manually inserted into a user object
     *
     *
     */
    private fun addToUserProfile() {
        //testing

        user?.email = "test"
        user?.password = "testpass"
        user?.firstName = dataList[FIRST_NAME]?.data.toString()
        user?.lastName = dataList[LAST_NAME]?.data.toString()
        user?.birthday = dataList[AGE]?.data.toString()
        user?.sex = dataList[SEX]?.data.toString()
        user?.height = dataList[HEIGHT]?.data.toString()
        user?.weight = dataList[WEIGHT]?.data.toString()
        user?.country = dataList[COUNTRY]?.data.toString()
        user?.city = dataList[CITY]?.data.toString()



        user?.let { viewModel.update(this, it) }
    }

    override fun onDataPass(data: Data) {
        if (data.data.toString().isEmpty()) {
            dataList?.remove(data.sender)
            nextButton?.isEnabled = false
        } else {
            dataList?.set(data.sender, data)
            if (dataList!!.size % 2==0) {
                nextButton?.isEnabled = true
            }
        }
    }

}

private operator fun Any.setValue(newUserInfoScreen: NewUserInfoScreen, property: KProperty<*>, lyfViewModel: LyfViewModel) {

}

