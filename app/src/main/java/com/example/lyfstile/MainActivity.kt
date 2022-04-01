package com.example.lyfstile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    private var lyfViewModel: LyfViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        lyfViewModel = ViewModelProvider(this)[LyfViewModel::class.java]
        lyfViewModel?.updateContained(5)
    }
}