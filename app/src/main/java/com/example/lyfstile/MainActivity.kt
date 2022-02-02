package com.example.lyfstile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import org.w3c.dom.Text




class MainActivity : AppCompatActivity(),  View.OnClickListener, TextSubmitFragment.PassData {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val beginAccCreation = findViewById<View>(R.id.new_user) as Button
       // beginAccCreation.setOnClickListener(this);

        var subfrag = TextSubmitFragment()

        var fragTrans = supportFragmentManager.beginTransaction();

        fragTrans.replace(R.id.text_frag_container,subfrag,"subfrag")
        fragTrans.commit()


    }

     override fun onClick(view : View)
    {
        when(view.id) {
            R.id.new_user ->
            {
                //val accountCreation = Intent(this, NewUserStepOne::class.java)
                //startActivity(accountCreation);


            }
        }
    }

    override fun onDataPass(data: Data) {

        //Reward them for submitting their names
        val toast = Toast.makeText(this, data.data["frag"], Toast.LENGTH_SHORT)
        toast.show()
    }


}