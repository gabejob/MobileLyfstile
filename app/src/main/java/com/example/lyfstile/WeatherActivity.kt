package com.example.lyfstile

import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class WeatherActivity : AppCompatActivity(), View.OnClickListener, PassData,
    ActionbarFragment.ClickInterface {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private var longitude = 0.0
    private var latitude = 0.0
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val actionbarFragment = ActionbarFragment()
        val fragtrans = supportFragmentManager.beginTransaction()
        fragtrans.replace(R.id.action_bar_fragment, actionbarFragment, ACTION_BAR)
        fragtrans.commit()
        actionbarFragment.bindClickInterface(this)

        val url = URL("http://www.android.com/")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        try {
            val input: InputStream = BufferedInputStream(urlConnection.getInputStream())
            input.read()
        } finally {
            urlConnection.disconnect()
        }
    }

    override fun onClick(view: View) {
    }

    override fun onDataPass(data: Data) {
    }

    override fun actionButtonClicked(id: Int) {
        val temp = User()
        temp.tempConstruct()
        when (id) {
            R.id.health -> {
                val healthScreen = Intent(this, HealthActivity::class.java)
                healthScreen.putExtra(USER_DATA, temp)
                // TODO - Remember to comment this back in. We need it for when we tie a database with images into this -jm
//                healthScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(healthScreen)
            }
            R.id.hiker -> {
                val mapScreen = Intent(this, MapActivity::class.java)
                this.startActivity(mapScreen)
            }
        }
    }
}