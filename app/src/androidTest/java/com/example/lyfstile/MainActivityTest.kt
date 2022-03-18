package com.example.lyfstile

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule

import org.junit.Test
import org.junit.Rule

class MainActivityTest {

    /*    private lateinit var scenario: ActivityScenario<MainActivity>

        @Before
        fun setup(){
            scenario = launchActivity()
            scenario.moveToState(Lifecycle.State.RESUME)
        }*/
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testOnCreate() {
        onView(withId(R.id.Log_in)).check(matches(isDisplayed()))
        onView(withId(R.id.new_user)).check(matches(isDisplayed()))
        onView(withId(R.id.forgot_pass)).check(matches(isDisplayed()))
    }

    @Test
    fun createMainAndClickLogin() {
        onView(withId(R.id.Log_in)).perform(click())
        onView(withId(R.id.account_text)).check(matches(isDisplayed()))
        //Intents.init();
        //intended(hasComponent(LoginExistingAccount::class.java.name))
    }

    @Test
    fun createMainAndClickNewUser() {
        onView(withId(R.id.new_user)).perform(click())
        onView(withId(R.id.welcome_group)).check(matches(isDisplayed()))
    }
}