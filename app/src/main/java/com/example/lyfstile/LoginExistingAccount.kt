package com.example.lyfstile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class LoginExistingAccount : AppCompatActivity(), View.OnClickListener, PassData {

    private var dataList = HashMap<String, Data>()
    private var login : Button ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_existing_account)

        var emailEnterFragment = TextSubmitFragment()
        var passwordEnterFragment = TextSubmitFragment()

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box,emailEnterFragment,"Email_box")
        fragtrans.replace(R.id.password_enter_box,passwordEnterFragment,"Password_box")

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

        if(senders.contains("Email_box") && senders.contains("Password_box"))
            return true

        return false
    }

    override fun onClick(view: View?) {
        var message = ""


       when(view?.id) {

          R.id.Login_button -> {
              //Make sure everything has been entered/initialized
              if (dataList.size == 2) {

                  var username = dataList["Email_box"]?.data
                  var password = dataList["Password_box"]?.data

                  if(verifyCredentials(username.toString(),password.toString())) {

                      for (entry in dataList) {
                          message += entry.value.data
                      }
                      //launch next activity


                      val homeScreen = Intent(this, HomeScreen::class.java)
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
        if (email == "123" && password == "123")
            return true

        return false
    }


    override fun onDataPass(_data: Data) {
        Toast.makeText(this, "Came from: " + _data.sender, Toast.LENGTH_SHORT).show()

        if(_data.data.isEmpty()) {
            dataList.remove(_data.sender)
            login?.isEnabled = false

        }
        else
        {
            dataList[_data.sender] = _data

            if(dataList.size == 2) {
                login?.isEnabled = true
            }

        }



    }


    }
