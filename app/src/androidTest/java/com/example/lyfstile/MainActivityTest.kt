package com.example.lyfstile

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Test
import org.junit.Rule

class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun createMainAndClick(){

        onView(withId(R.id.Log_in)).perform(click())
        Intents.init();
        intended(hasComponent(LoginExistingAccount::class.java.name))
    }

    fun testOnCreate() {}
    fun testOnClick() {}
}