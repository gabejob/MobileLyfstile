package com.example.lyfstile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class LoginExistingAccount : AppCompatActivity(), View.OnClickListener, PassData {

    private var dataList = HashMap<String, Data>()
    private var login : Button ?= null
    private var testLogin : User ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_existing_account)

        var emailEnterFragment = TextSubmitFragment()
        var passwordEnterFragment = TextSubmitFragment()

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box,emailEnterFragment, EMAIL)
        fragtrans.replace(R.id.password_enter_box,passwordEnterFragment, PASSWORD)

        fragtrans.commit()

        login = findViewById<Button>(R.id.Login_button)
        login?.isEnabled = false

        val forgot = findViewById<Button>(R.id.forgot_pass)
        forgot.setOnClickListener(this)
        login?.setOnClickListener(this)
    }


    private fun allBoxesEntered(): Boolean {
        var senders = ArrayList<String>()


        if (dataList != null) {
            for (entry in dataList) {
                var temp = entry as Data
                senders.add(temp.sender)

            }
        }

        if(senders.contains(EMAIL) && senders.contains(PASSWORD))
            return true

        return false
    }

    override fun onClick(view: View?) {
        var message = ""


       when(view?.id) {

          R.id.Login_button -> {
              //Make sure everything has been entered/initialized
              if (dataList.size == 2) {

                  var username = dataList[EMAIL]?.data
                  var password = dataList[PASSWORD]?.data

                  if(verifyCredentials(username.toString(),password.toString())) {

                      for (entry in dataList) {
                          message += entry.value.data
                      }
                      //launch next activity


                      val homeScreen = Intent(this, HomeScreen::class.java)
                      homeScreen.putExtra("usr_data",testLogin)
                      this.startActivity(homeScreen)


                  }
                  else
                  {
                      Toast.makeText(this, "Incorrect Password!", Toast.LENGTH_SHORT).show()

                  }
              } else {
                  Toast.makeText(this, "Please enter all forms!", Toast.LENGTH_SHORT).show()
              }
          }
           R.id.forgot_pass ->
           {

               Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show()

           }

       }
    }

    /*
    *
    * Need to actually authorize data once a DB is established,
    * for now, test account credentials are 123:123
    *
    *
    * */
    private fun verifyCredentials(email : String, password : String) : Boolean
    {
        if (email == "123@123.com" && password == "123") {
         var gabe = User()
            gabe.firstName="gabe"
            gabe.lastName="gabe"
            gabe.birthday= "2/2/1998"
            gabe.sex="Male"
            gabe.height="6 ft, 1 in."
            gabe.weight="181 lbs, 0 oz"
            testLogin = gabe
            return true
        }
        return false
    }


    override fun onDataPass(data: Data) {
        //Toast.makeText(this, "Came from: " + _data.sender, Toast.LENGTH_SHORT).show()

        if(data.data.isEmpty()) {
            dataList.remove(data.sender)
            login?.isEnabled = false

        }
        else
        {
            dataList[data.sender] = data

            if(dataList.size == 2) {
                login?.isEnabled = true
            }

        }



    }


    }
