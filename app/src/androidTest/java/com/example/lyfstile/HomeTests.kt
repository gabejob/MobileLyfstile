package com.example.lyfstile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeTests {

    fun addition_isCorrect() {
        val newUser = User("James", "Murray", "JM@JM.com", "cooldude1", "12/17", "M", "5'11", "195", "USA", "Seattle", "")
        val intent = Intent(ApplicationProvider.getApplicationContext(), HomeScreen::class.java)
            .putExtra("USER_DATA", newUser)
        AppCompatActivity().startActivity(intent)

        Assert.assertEquals(4, 2 + 2)
    }
}