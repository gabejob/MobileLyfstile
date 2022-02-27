package com.example.lyfstile

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class NewUserInfoScreenTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(NewUserInfoScreen::class.java)

    private val newUser = User("James", "Murray", "JM@JM.com", "cooldude1", "12/17", "M", "5'11", "195", "USA", "Seattle", "")

    @Test
    fun onCreate() {
        Espresso.onView(ViewMatchers.withId(R.id.wyn_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun onClick() {
    }

    @Test
    fun onBackPressed() {
    }

    @Test
    fun onDataPass() {
    }
}