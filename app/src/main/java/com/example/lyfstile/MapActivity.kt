package com.example.lyfstile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MapActivity : AppCompatActivity(), View.OnClickListener, PassData, LocationListener {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private var longitude = 0.0
    private var latitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapsHikingButton = findViewById<Button>(R.id.Maps_Hiking)
        mapsHikingButton.setOnClickListener(this)
        val mapsParksButton = findViewById<Button>(R.id.Maps_Parks)
        mapsParksButton.setOnClickListener(this)
        val mapsGymsButton = findViewById<Button>(R.id.Maps_Gym)
        mapsGymsButton.setOnClickListener(this)
        val mapsRestaurantsButton = findViewById<Button>(R.id.Maps_Restaurants)
        mapsRestaurantsButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.Maps_Hiking -> {
                val searchURI = Uri.parse("geo:$latitude,$longitude?q=Hiking")
                val mapIntent = Intent(Intent.ACTION_VIEW, searchURI)
                startActivity(mapIntent)
            }
            R.id.Maps_Parks -> {
                val searchURI = Uri.parse("geo:$latitude,$longitude?q=Parks")
                val mapIntent = Intent(Intent.ACTION_VIEW, searchURI)
                startActivity(mapIntent)
            }
            R.id.Maps_Gym -> {
                val searchURI = Uri.parse("geo:$latitude,$longitude?q=Gym")
                val mapIntent = Intent(Intent.ACTION_VIEW, searchURI)
                startActivity(mapIntent)
            }
            R.id.Maps_Restaurants -> {
                val searchURI = Uri.parse("geo:$latitude,$longitude?q=Restaurants")
                val mapIntent = Intent(Intent.ACTION_VIEW, searchURI)
                startActivity(mapIntent)
            }
        }
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onDataPass(data: Data) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
    }
}