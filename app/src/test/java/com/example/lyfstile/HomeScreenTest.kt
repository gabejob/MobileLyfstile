package com.example.lyfstile

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert

class HomeScreenTest {

    // Create test user with no profile picture
    val newUser = User("James", "Murray", "JM@JM.com", "cooldude1", "12/17", "M", "5'11", "195", "USA", "Seattle", "")
    // pass the test user to home activity
    @get:Rule val intent = Intent(ApplicationProvider.getApplicationContext(), HomeScreen::class.java)
        .putExtra("USER_DATA", newUser)

    @get:Rule
    val rule: ActivityScenarioRule<*>? = ActivityScenarioRule(HomeScreen::class.java)

    @Test
    fun ensureDataCanBeRetrived() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), HomeScreen::class.java)
            .putExtra("USER_DATA", newUser)
        var user : User ?= null
        user = Intent(ApplicationProvider.getApplicationContext(), HomeScreen::class.java).extras?.get("USER_DATA") as User

        Assert.assertEquals("James", user.firstName)
        Assert.assertEquals("Murray", user.lastName)
        Assert.assertEquals("JM@JM.com", user.email)
        Assert.assertEquals("cooldude1", user.password)
        Assert.assertEquals("12/17", user.birthday)
        Assert.assertEquals("M", user.sex)
        Assert.assertEquals("5'11", user.height)
        Assert.assertEquals("195", user.weight)
        Assert.assertEquals("USA", user.country)
        Assert.assertEquals("Seattle", user.city)
        Assert.assertEquals("", user.pfp)
    }

    @Test
    fun testHealthClick(){
        onView(withId(R.id.health)).perform(click())
        var healthActiv = HealthActivity()
        Assert.assertTrue(healthActiv.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED))
    }
}