package com.example.lyfstile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlin.collections.HashMap

class LoginExistingAccount : AppCompatActivity(), View.OnClickListener, PassData {
    private var dataList = HashMap<String, Data>()
    private var login: Button? = null
    private var testLogin: User? = null
    private var viewModel : ViewModel ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_existing_account)

        viewModel = ViewModelProvider( this).get(ViewModel::class.java)


        val emailEnterFragment = TextSubmitFragment()
        val passwordEnterFragment = TextSubmitFragment()

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box, emailEnterFragment, EMAIL)
        fragtrans.replace(R.id.password_enter_box, passwordEnterFragment, PASSWORD)

        fragtrans.commit()

        login = findViewById<Button>(R.id.Login_button)
        login?.isEnabled = false

        val forgot = findViewById<Button>(R.id.forgot_pass)
        forgot.setOnClickListener(this)
        login?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.Login_button -> {
                // Make sure everything has been entered/initialized
                if (dataList.size == 2) {
                    val username = dataList[EMAIL]?.data
                    val password = dataList[PASSWORD]?.data

                    if (verifyCredentials(username.toString(), password.toString())) {

                        val homeScreen = Intent(this, HomeScreen::class.java)
                        homeScreen.putExtra(USER_DATA, testLogin)
                        this.startActivity(homeScreen)
                    } else {
                        Toast.makeText(this, "Incorrect Password!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please enter all forms!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.forgot_pass -> {
                Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*
    *
    * Need to actually authorize data once a DB is established,
    * for now, test account credentials are 123:123
    * TODO - Fix this to be dynamic - jm
    *
    * */
    private fun verifyCredentials(email: String, password: String): Boolean {
        if (email == "123@123.com" && password == "Playing123!") {
            testLogin = User()
            testLogin?.tempConstruct()
            return true
        }
        return false
    }

    override fun onDataPass(data: Data) {
        if (data.data.toString().isEmpty()) {
            dataList.remove(data.sender)
            login?.isEnabled = false
        } else {
            dataList[data.sender] = data

            if (dataList.size == 2) {
                login?.isEnabled = true
            }
        }
    }
}
